package com.portfolioforecasting.service.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.portfolioforecasting.dto.response.DashboardResponse;
import com.portfolioforecasting.dto.response.HoldingAnalyticsResponse;
import com.portfolioforecasting.dto.stock.StockResponse;
import com.portfolioforecasting.entity.Holding;
import com.portfolioforecasting.repository.HoldingRepository;
import com.portfolioforecasting.service.AnalyticsService;
import com.portfolioforecasting.service.MarketDataService;

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
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        List<HoldingAnalyticsResponse> holdingAnalytics = new ArrayList<>();
        for (Holding holding : holdings) {
            holdingAnalytics.add(calculateHoldingAnalytics(holding));
        }
        return null;

    }

    private HoldingAnalyticsResponse calculateHoldingAnalytics(Holding holding) {
        StockResponse stock = marketDataService.getStock(holding.getStockSymbol());
        BigDecimal investment = calculateInvestment(holding);
        BigDecimal currentValue = calculateCurrentValue(holding, stock.getCurrentPrice());
        BigDecimal profit = calculateProfit(investment, currentValue);
        BigDecimal returnPercentage = calculateReturnPercentage(investment, profit);

        return HoldingAnalyticsResponse.builder()
                .stockSymbol(holding.getStockSymbol())
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
        if (investment.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return profit
                .divide(investment, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .stripTrailingZeros();
    }

}
