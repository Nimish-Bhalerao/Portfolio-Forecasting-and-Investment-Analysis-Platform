package com.portfolioforecasting.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.client.FmpClient;
import com.portfolioforecasting.dto.response.FundamentalResponse;
import com.portfolioforecasting.dto.response.ProfileResponse;
import com.portfolioforecasting.service.FundamentalService;

@Service
public class FundamentalServiceImpl implements FundamentalService {

    private final FmpClient fmpClient;

    public FundamentalServiceImpl(FmpClient fmpClient) {
        this.fmpClient = fmpClient;
    }

    @Override
    public FundamentalResponse getFundamentals(String symbol) {

        ProfileResponse[] profiles = fmpClient.getCompanyProfile(symbol);

        if (profiles == null || profiles.length == 0) {
            throw new RuntimeException("Company not found: " + symbol);
        }

        ProfileResponse profile = profiles[0];

        return FundamentalResponse.builder()
                .symbol(profile.getSymbol())
                .companyName(profile.getCompanyName())
                .sector(profile.getSector())
                .industry(profile.getIndustry())
                .marketCap(profile.getMarketCap())
                .build();
    }
}