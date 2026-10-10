package com.portfolioforecasting.client;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.portfolioforecasting.dto.response.HistoricalPriceResponse;
import com.portfolioforecasting.dto.response.ProfileResponse;
import com.portfolioforecasting.dto.response.RatiosResponse;

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

        public RatiosResponse[] getRatios(String symbol) {

                return restClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("https")
                                                .host("financialmodelingprep.com")
                                                .path("/stable/ratios-ttm")
                                                .queryParam("symbol", symbol)
                                                .queryParam("apikey", apiKey)
                                                .build())
                                .retrieve()
                                .body(RatiosResponse[].class);
        }

        public HistoricalPriceResponse[] getHistoricalPrices(
                        String symbol, LocalDate from, LocalDate to) {

                return restClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("https")
                                                .host("financialmodelingprep.com")
                                                .path("/stable/historical-price-eod/full")
                                                .queryParam("symbol", symbol)
                                                .queryParam("from", from)
                                                .queryParam("to", to)
                                                .queryParam("apikey", apiKey)
                                                .build())
                                .retrieve()
                                .body(HistoricalPriceResponse[].class);
        }

}