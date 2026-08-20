package com.portfolioforecasting.dto.response;

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
public class FundamentalResponse {

    private String symbol;
    private String companyName;
    private String sector;
    private String industry;
    private BigDecimal marketCap;
    private BigDecimal peRatio;
    private BigDecimal roe;
    private BigDecimal debtToEquity;
    private BigDecimal currentRatio;
    private BigDecimal netProfitMargin;
}

