package com.portfolioforecasting.service.serviceImpl;

import java.math.BigDecimal;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.portfolioforecasting.client.FinnhubClient;
import com.portfolioforecasting.dto.stock.FinnhubQuoteResponse;
import com.portfolioforecasting.dto.stock.StockResponse;
import com.portfolioforecasting.service.MarketDataService;

@Service
public class MarketDataServiceImpl implements MarketDataService {
    private final FinnhubClient finnhubClient;

    public MarketDataServiceImpl(FinnhubClient finnhubClient) {
        this.finnhubClient = finnhubClient; 
    }
    @Cacheable(value = "stockQuotes", key = "#symbol")
    @Override
public StockResponse getStock(String symbol) {

    FinnhubQuoteResponse response = finnhubClient.getQuote(symbol);

    if (response == null || response.getC() == null || response.getC().compareTo(BigDecimal.ZERO) == 0) {
        throw new RuntimeException("Stock Data not found for : " + symbol);
    }

    return StockResponse.builder()
            .symbol(symbol)
            .companyName(symbol)      // Temporary
            .currentPrice(response.getC())
            .build();
}







}
