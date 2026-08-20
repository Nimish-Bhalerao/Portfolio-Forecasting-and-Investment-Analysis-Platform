package com.portfolioforecasting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolioforecasting.dto.stock.AlphaVantageResponse;
import com.portfolioforecasting.dto.stock.StockResponse;
import com.portfolioforecasting.service.MarketDataService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final MarketDataService marketDataService;

    public StockController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/{symbol}")
    public StockResponse getStock(@PathVariable String symbol) {
        return marketDataService.getStock(symbol);
    }
}
