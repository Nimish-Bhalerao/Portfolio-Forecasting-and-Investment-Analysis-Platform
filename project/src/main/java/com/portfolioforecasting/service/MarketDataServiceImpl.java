package com.portfolioforecasting.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.dto.stock.StockResponse;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    @Override
    public StockResponse getStock(String symbol) {
        return StockResponse.builder()
                .symbol(symbol)
                .companyName("Dummy Company")
                .currentPrice(BigDecimal.valueOf(4200))
                .build();
    }

}
