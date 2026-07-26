package com.portfolioforecasting.service.serviceImpl;

import java.math.BigDecimal;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.portfolioforecasting.client.AlphaVantageClient;
import com.portfolioforecasting.dto.stock.AlphaVantageResponse;
import com.portfolioforecasting.dto.stock.GlobalQuote;
import com.portfolioforecasting.dto.stock.StockResponse;
import com.portfolioforecasting.service.MarketDataService;

@Service
public class MarketDataServiceImpl implements MarketDataService {
    private final AlphaVantageClient alphaVantageClient;

    public MarketDataServiceImpl(AlphaVantageClient alphaVantageClient) {
        this.alphaVantageClient = alphaVantageClient;
    }

    @Override
    public StockResponse getStock(String symbol) {
        AlphaVantageResponse response = alphaVantageClient.getGlobalQuote(symbol);
        if (response == null || response.getGlobalQuote() == null) {
            throw new RuntimeException("Stock Data not found for : " + symbol);
        }
        GlobalQuote quote = response.getGlobalQuote();
        return StockResponse.builder()
                .symbol(quote.getSymbol())
                .companyName(quote.getSymbol()) // Temporary
                .currentPrice(new BigDecimal(quote.getPrice()))
                .build();

    }

}
