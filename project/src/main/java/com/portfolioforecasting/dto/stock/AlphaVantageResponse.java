package com.portfolioforecasting.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlphaVantageResponse {
    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

}
