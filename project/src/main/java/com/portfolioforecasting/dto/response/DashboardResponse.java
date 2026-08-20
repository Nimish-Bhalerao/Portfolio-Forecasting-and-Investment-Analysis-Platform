package com.portfolioforecasting.dto.response;

import java.math.BigDecimal;
import java.util.List;

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
public class DashboardResponse {
    private BigDecimal totalInvestment;
    private BigDecimal currentValue;
    private BigDecimal unrealizedProfit;
    private BigDecimal returnPercentage;
    private List<HoldingAnalyticsResponse> holdings;
}
