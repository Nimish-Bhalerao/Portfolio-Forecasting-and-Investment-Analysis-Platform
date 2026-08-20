package com.portfolioforecasting.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.portfolioforecasting.dto.response.ProfileResponse;

@Component
public class FmpClient {

    private final RestClient restClient;

    @Value("${fmp.api.key}")
    private String apiKey;

    public FmpClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProfileResponse[] getCompanyProfile(String symbol) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("financialmodelingprep.com")
                        .path("/stable/profile")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .body(ProfileResponse[].class);
    }
}