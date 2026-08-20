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
public class ProfileResponse {

    private String symbol;

    private String companyName;

    private String sector;

    private String industry;

    @JsonProperty("mktCap")
    @JsonAlias({"marketCap", "mktCap"})
    private BigDecimal marketCap;
}