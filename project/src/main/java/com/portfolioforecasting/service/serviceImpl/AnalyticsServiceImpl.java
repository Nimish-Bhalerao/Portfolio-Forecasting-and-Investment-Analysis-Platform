package com.portfolioforecasting.service.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.portfolioforecasting.client.FmpClient;
import com.portfolioforecasting.dto.response.DashboardResponse;
import com.portfolioforecasting.dto.response.HistoricalPriceResponse;
import com.portfolioforecasting.dto.response.HistoricalReturnsResponse;
import com.portfolioforecasting.dto.response.HoldingAnalyticsResponse;
import com.portfolioforecasting.dto.stock.StockResponse;
import com.portfolioforecasting.entity.Holding;
import com.portfolioforecasting.repository.HoldingRepository;
import com.portfolioforecasting.service.AnalyticsService;
import com.portfolioforecasting.service.MarketDataService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final HoldingRepository holdingRepository;
    private final MarketDataService marketDataService;
    private final FmpClient fmpClient;

    public AnalyticsServiceImpl(HoldingRepository holdingRepository,
            MarketDataService marketDataService,
            FmpClient fmpClient) {
        this.holdingRepository = holdingRepository;
        this.marketDataService = marketDataService;
        this.fmpClient = fmpClient;
    }

    @Override
    public DashboardResponse getPortfolioAnalytics(Long portfolioId) {

        // Fetch holdings
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);

        if (holdings.isEmpty()) {
            throw new RuntimeException("No holdings found for portfolio id: " + portfolioId);
        }

        // Calculate analytics for each holding
        List<HoldingAnalyticsResponse> holdingAnalyticsList = new ArrayList<>();

        for (Holding holding : holdings) {
            holdingAnalyticsList.add(calculateHoldingAnalytics(holding));
        }

        // Aggregate totals
        BigDecimal totalInvestment = holdingAnalyticsList.stream()
                .map(HoldingAnalyticsResponse::getInvestment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCurrentValue = holdingAnalyticsList.stream()
                .map(HoldingAnalyticsResponse::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProfit = totalCurrentValue.subtract(totalInvestment);

        BigDecimal portfolioReturn = calculateReturnPercentage(totalInvestment, totalProfit);

        // Build dashboard response
        return DashboardResponse.builder()
                .holdings(holdingAnalyticsList)
                .totalInvestment(totalInvestment)
                .currentValue(totalCurrentValue)
                .unrealizedProfit(totalProfit)
                .returnPercentage(portfolioReturn)
                .build();
    }

    private HoldingAnalyticsResponse calculateHoldingAnalytics(Holding holding) {
        StockResponse stock = marketDataService.getStock(holding.getStockSymbol());
        BigDecimal investment = calculateInvestment(holding);
        BigDecimal currentValue = calculateCurrentValue(holding, stock.getCurrentPrice());
        BigDecimal profit = calculateProfit(investment, currentValue);
        BigDecimal returnPercentage = calculateReturnPercentage(investment, profit);

        return HoldingAnalyticsResponse.builder()
                .stockSymbol(holding.getStockSymbol())
                .companyName(holding.getCompanyName() != null ? holding.getCompanyName() : stock.getCompanyName())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .currentPrice(stock.getCurrentPrice())
                .investment(investment)
                .currentValue(currentValue)
                .profit(profit)
                .returnPercentage(returnPercentage)
                .build();
    }

    private BigDecimal calculateInvestment(Holding holding) {
        return holding.getBuyPrice()
                .multiply(BigDecimal.valueOf(holding.getQuantity()));
    }

    private BigDecimal calculateCurrentValue(Holding holding, BigDecimal currentPrice) {
        return currentPrice.multiply(
                BigDecimal.valueOf(holding.getQuantity()));
    }

    private BigDecimal calculateProfit(BigDecimal investment, BigDecimal currentValue) {
        return currentValue.subtract(investment);
    }

    private BigDecimal calculateReturnPercentage(BigDecimal investment, BigDecimal profit) {
        if (investment == null || investment.compareTo(BigDecimal.ZERO) == 0 || profit == null) {
            return BigDecimal.ZERO;
        }
        return profit
                .multiply(BigDecimal.valueOf(100))
                .divide(investment, 2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricalReturnsResponse getHistoricalReturns(
            Long portfolioId,
            LocalDate from,
            LocalDate to) {

        if (from == null || to == null || !from.isBefore(to)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "'from' must be earlier than 'to'");
        }

        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);

        if (holdings == null || holdings.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No holdings found for portfolio " + portfolioId);
        }

        // Without transaction history, require every holding to have
        // been purchased on or before the start date.
        boolean holdingBoughtLater = holdings.stream()
                .anyMatch(h -> h.getPurchaseDate() != null
                        && h.getPurchaseDate().isAfter(from));

        if (holdingBoughtLater) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The start date must be on or after every holding's "
                            + "purchase date. Historical transactions are not "
                            + "yet tracked.");
        }

        BigDecimal initialValue = BigDecimal.ZERO;
        BigDecimal finalValue = BigDecimal.ZERO;

        for (Holding holding : holdings) {
            String symbol = holding.getStockSymbol();
            BigDecimal quantity = BigDecimal.valueOf(holding.getQuantity());

            // Start 10 days earlier to find a prior trading-day price.
            LocalDate fetchFrom = from.minusDays(10);

            HistoricalPriceResponse[] prices = fmpClient.getHistoricalPrices(symbol, fetchFrom, to);

            if (prices == null || prices.length == 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "No historical prices returned for " + symbol);
            }

            BigDecimal startPrice = findPriceOnOrBefore(prices, from, symbol);
            BigDecimal endPrice = findPriceOnOrBefore(prices, to, symbol);

            initialValue = initialValue.add(startPrice.multiply(quantity));
            finalValue = finalValue.add(endPrice.multiply(quantity));
        }

        if (initialValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Initial portfolio value must be positive");
        }

        long days = ChronoUnit.DAYS.between(from, to);

        BigDecimal totalReturn = finalValue
                .subtract(initialValue)
                .divide(initialValue, 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        double valueRatio = finalValue.divide(initialValue, 12, RoundingMode.HALF_UP)
                .doubleValue();

        double annualizedRate = (Math.pow(valueRatio, 365.25 / days) - 1.0) * 100.0;

        BigDecimal annualizedReturn = BigDecimal.valueOf(annualizedRate)
                .setScale(2, RoundingMode.HALF_UP);

        return new HistoricalReturnsResponse(
                portfolioId,
                from,
                to,
                initialValue.setScale(2, RoundingMode.HALF_UP),
                finalValue.setScale(2, RoundingMode.HALF_UP),
                totalReturn,
                annualizedReturn,
                days);
    }

    private BigDecimal findPriceOnOrBefore(
            HistoricalPriceResponse[] prices,
            LocalDate targetDate,
            String symbol) {

        return Arrays.stream(prices)
                .filter(p -> p.getDate() != null)
                .filter(p -> !p.getDate().isAfter(targetDate))
                .filter(p -> p.getClose() != null)
                .max((a, b) -> a.getDate().compareTo(b.getDate()))
                .map(HistoricalPriceResponse::getClose)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "No price on or before " + targetDate
                                + " for " + symbol));
    }

}