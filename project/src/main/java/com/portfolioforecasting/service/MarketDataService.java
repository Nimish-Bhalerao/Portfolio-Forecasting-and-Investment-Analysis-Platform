package com.portfolioforecasting.service;

import com.portfolioforecasting.dto.stock.StockResponse;

public interface MarketDataService {
    public StockResponse getStock(String symbol);
}
