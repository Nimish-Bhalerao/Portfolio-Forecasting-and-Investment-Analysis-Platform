package com.portfolioforecasting.dto.stock;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinnhubQuoteResponse {

    // Current Price
    private BigDecimal c;

    // Price Change
    private BigDecimal d;

    // Percentage Change
    private BigDecimal dp;

    // High Price
    private BigDecimal h;

    // Low Price
    private BigDecimal l;

    // Open Price
    private BigDecimal o;

    // Previous Close
    private BigDecimal pc;

    // Timestamp
    private Long t;
}