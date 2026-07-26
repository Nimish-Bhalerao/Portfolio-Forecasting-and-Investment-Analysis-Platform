package com.portfolioforecasting.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.portfolioforecasting.dto.stock.AlphaVantageResponse;

@Component
public class AlphaVantageClient {

    private final RestClient restClient;

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    public AlphaVantageClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public AlphaVantageResponse getGlobalQuote(String symbol) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("www.alphavantage.co")
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .body(AlphaVantageResponse.class);
    }
}
