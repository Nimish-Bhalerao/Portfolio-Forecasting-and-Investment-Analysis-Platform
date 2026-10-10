package com.portfolioforecasting.service;

import java.time.LocalDate;

import com.portfolioforecasting.dto.response.DashboardResponse;
import com.portfolioforecasting.dto.response.HistoricalReturnsResponse;

public interface AnalyticsService {
    DashboardResponse getPortfolioAnalytics(Long portfolioId);

    HistoricalReturnsResponse getHistoricalReturns(
            Long portfolioId,
            LocalDate from,
            LocalDate to);

}
