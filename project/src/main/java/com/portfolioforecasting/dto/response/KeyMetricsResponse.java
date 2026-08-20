package com.portfolioforecasting.dto.response;

import java.math.BigDecimal;

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
public class KeyMetricsResponse {

    @JsonProperty("revenuePerShareTTM")
    @JsonAlias({"revenuePerShare", "revenuePerShareTTM"})
    private BigDecimal revenuePerShare;

    @JsonProperty("netIncomePerShareTTM")
    @JsonAlias({"netIncomePerShare", "netIncomePerShareTTM"})
    private BigDecimal netIncomePerShare;

    @JsonProperty("bookValuePerShareTTM")
    @JsonAlias({"bookValuePerShare", "bookValuePerShareTTM"})
    private BigDecimal bookValuePerShare;

    @JsonProperty("freeCashFlowPerShareTTM")
    @JsonAlias({"freeCashFlowPerShare", "freeCashFlowPerShareTTM"})
    private BigDecimal freeCashFlowPerShare;

    @JsonProperty("cashPerShareTTM")
    @JsonAlias({"cashPerShare", "cashPerShareTTM"})
    private BigDecimal cashPerShare;

    @JsonProperty("dividendYieldTTM")
    @JsonAlias({"dividendYield", "dividendYieldPercentage", "dividendYieldTTM"})
    private BigDecimal dividendYield;

    @JsonProperty("payoutRatioTTM")
    @JsonAlias({"payoutRatio", "payoutRatioTTM"})
    private BigDecimal payoutRatio;

    @JsonProperty("enterpriseValueTTM")
    @JsonAlias({"enterpriseValue", "enterpriseValueTTM"})
    private BigDecimal enterpriseValue;

    @JsonProperty("evToSalesTTM")
    @JsonAlias({"evToSales", "evToSalesTTM"})
    private BigDecimal evToSales;

    @JsonProperty("evToOperatingCashFlowTTM")
    @JsonAlias({"evToOperatingCashFlow", "evToOperatingCashFlowTTM"})
    private BigDecimal evToOperatingCashFlow;

    @JsonProperty("evToFreeCashFlowTTM")
    @JsonAlias({"evToFreeCashFlow", "evToFreeCashFlowTTM"})
    private BigDecimal evToFreeCashFlow;

    @JsonProperty("earningsYieldTTM")
    @JsonAlias({"earningsYield", "earningsYieldTTM"})
    private BigDecimal earningsYield;

    @JsonProperty("freeCashFlowYieldTTM")
    @JsonAlias({"freeCashFlowYield", "freeCashFlowYieldTTM"})
    private BigDecimal freeCashFlowYield;

    @JsonProperty("roicTTM")
    @JsonAlias({"roic", "returnOnInvestedCapital", "roicTTM"})
    private BigDecimal roic;

    @JsonProperty("debtToEquityTTM")
    @JsonAlias({"debtToEquity", "debtEquityRatio", "debtToEquityTTM"})
    private BigDecimal debtToEquity;

    @JsonProperty("netDebtToEBITDATTM")
    @JsonAlias({"netDebtToEBITDA", "netDebtToEBITDATTM"})
    private BigDecimal netDebtToEBITDA;
}

