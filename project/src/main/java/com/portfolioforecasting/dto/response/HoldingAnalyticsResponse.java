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
public class HoldingAnalyticsResponse {
    private String stockSymbol;
    private String companyName;
    private Integer quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice;
    private BigDecimal investment;
    private BigDecimal currentValue;
    private BigDecimal profit;
    private BigDecimal returnPercentage;
}

