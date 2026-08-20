package com.portfolioforecasting.service;

import java.util.List;

import com.portfolioforecasting.dto.request.HoldingRequest;
import com.portfolioforecasting.dto.response.HoldingResponse;
import com.portfolioforecasting.entity.Holding;

public interface HoldingService {
    HoldingResponse createHolding(HoldingRequest request);

    HoldingResponse getHoldingById(Long id);

    List<HoldingResponse> getAllHoldings();

    HoldingResponse updateHolding(Long id, HoldingRequest request);

    void deleteHolding(Long id);
}
