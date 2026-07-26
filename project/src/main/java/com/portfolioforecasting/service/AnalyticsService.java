package com.portfolioforecasting.service;

import com.portfolioforecasting.dto.response.DashboardResponse;

public interface AnalyticsService {
    DashboardResponse getPortfolioAnalytics(Long portfolioId);
}
