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
public class StockResponse {
    private String symbol;
    private String companyName;
    private BigDecimal currentPrice;
}
