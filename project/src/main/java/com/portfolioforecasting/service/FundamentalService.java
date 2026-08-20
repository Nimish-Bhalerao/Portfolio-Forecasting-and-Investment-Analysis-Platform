package com.portfolioforecasting.service;

import com.portfolioforecasting.dto.response.FundamentalResponse;

public interface FundamentalService {

    FundamentalResponse getFundamentals(String symbol);

}