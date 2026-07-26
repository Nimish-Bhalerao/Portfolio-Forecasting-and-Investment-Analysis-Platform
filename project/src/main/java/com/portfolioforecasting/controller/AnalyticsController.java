package com.portfolioforecasting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolioforecasting.dto.response.DashboardResponse;
import com.portfolioforecasting.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/portfolio/{id}")
    public DashboardResponse getPortfolioAnalytics(@PathVariable Long id) {
        return analyticsService.getPortfolioAnalytics(id);
    }

}