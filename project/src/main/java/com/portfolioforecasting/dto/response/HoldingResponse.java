package com.portfolioforecasting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponse {
    private Long id;
    private String stockSymbol;
    private String companyName;
    private Integer quantity;
    private BigDecimal buyPrice;
    private LocalDate purchaseDate;
}
