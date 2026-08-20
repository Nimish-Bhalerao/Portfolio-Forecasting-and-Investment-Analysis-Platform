package com.portfolioforecasting.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalQuote {
    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("05. price")
    private String price;

}
