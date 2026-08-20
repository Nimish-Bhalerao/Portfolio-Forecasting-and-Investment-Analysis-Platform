package com.portfolioforecasting.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private BigDecimal totalInvestment;

    private BigDecimal currentValue;

    private BigDecimal returnPercentage;

    private List<HoldingAnalyticsResponse> holdings;

}
