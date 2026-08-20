package com.portfolioforecasting.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.portfolioforecasting.dto.stock.FinnhubQuoteResponse;

@Component
public class FinnhubClient {

    private final RestClient restClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public FinnhubQuoteResponse getQuote(String symbol) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("finnhub.io")
                        .path("/api/v1/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .body(FinnhubQuoteResponse.class);
    }
}