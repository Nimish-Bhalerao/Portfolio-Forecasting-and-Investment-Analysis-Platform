package com.portfolioforecasting.dto.request;

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
public class HoldingRequest {
    private String stockSymbol;
    private String companyName;
    private Integer quantity;
    private BigDecimal buyPrice;
    private Long portfolioId;
}

