package com.portfolioforecasting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolioforecasting.dto.response.DashboardResponse;
import com.portfolioforecasting.dto.response.HistoricalReturnsResponse;
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

    @GetMapping("/portfolio/{id}/historical-returns")
    public ResponseEntity<HistoricalReturnsResponse> getHistoricalReturns(@PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return ResponseEntity.ok(
                analyticsService.getHistoricalReturns(id, from, to));
    }

}