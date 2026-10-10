package com.portfolioforecasting.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoricalReturnsResponse {

    private Long portfolioId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal initialValue;
    private BigDecimal finalValue;
    private BigDecimal totalReturnPercentage;
    private BigDecimal annualizedReturnPercentage;
    private long durationDays;
}
