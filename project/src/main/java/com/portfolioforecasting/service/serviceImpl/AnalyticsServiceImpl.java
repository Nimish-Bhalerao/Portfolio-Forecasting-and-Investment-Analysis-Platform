package com.portfolioforecasting.service.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.dto.response.DashboardResponse;
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

    public AnalyticsServiceImpl(HoldingRepository holdingRepository,
            MarketDataService marketDataService) {
        this.holdingRepository = holdingRepository;
        this.marketDataService = marketDataService;
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
}