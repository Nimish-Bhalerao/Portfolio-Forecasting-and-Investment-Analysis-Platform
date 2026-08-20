package com.portfolioforecasting.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatiosResponse {

    @JsonProperty("priceEarningsRatio")
    @JsonAlias({
        "priceToEarningsRatioTTM", "priceToEarningsRatio",
        "priceEarningsRatioTTM", "priceEarningsRatio",
        "peRatioTTM", "peRatio", "peTTM",
        "priceToEarningsDilutedRatioTTM"
    })
    private BigDecimal priceEarningsRatio;

    @JsonProperty("returnOnEquity")
    @JsonAlias({
        "returnOnEquityTTM", "returnOnEquity",
        "roeTTM", "roe"
    })
    private BigDecimal returnOnEquity;

    @JsonProperty("debtEquityRatio")
    @JsonAlias({
        "debtToEquityRatioTTM", "debtToEquityRatio",
        "debtToEquityTTM", "debtToEquity",
        "debtEquityRatioTTM", "debtEquityRatio",
        "totalDebtToEquityTTM"
    })
    private BigDecimal debtEquityRatio;

    @JsonProperty("currentRatio")
    @JsonAlias({"currentRatioTTM", "currentRatio"})
    private BigDecimal currentRatio;

    @JsonProperty("netProfitMargin")
    @JsonAlias({
        "netProfitMarginTTM", "netProfitMargin",
        "profitMargin", "continuousOperationsProfitMarginTTM",
        "bottomLineProfitMarginTTM"
    })
    private BigDecimal netProfitMargin;

    @JsonProperty("netIncomePerShareTTM")
    @JsonAlias({"netIncomePerShareTTM", "netIncomePerShare"})
    private BigDecimal netIncomePerShareTTM;

    @JsonProperty("bookValuePerShareTTM")
    @JsonAlias({
        "bookValuePerShareTTM", "bookValuePerShare",
        "shareholdersEquityPerShareTTM", "shareholdersEquityPerShare"
    })
    private BigDecimal bookValuePerShareTTM;

    public BigDecimal getReturnOnEquity() {
        if (returnOnEquity != null) {
            return returnOnEquity;
        }
        if (netIncomePerShareTTM != null && bookValuePerShareTTM != null && bookValuePerShareTTM.compareTo(BigDecimal.ZERO) != 0) {
            return netIncomePerShareTTM.divide(bookValuePerShareTTM, 4, RoundingMode.HALF_UP);
        }
        return null;
    }
}