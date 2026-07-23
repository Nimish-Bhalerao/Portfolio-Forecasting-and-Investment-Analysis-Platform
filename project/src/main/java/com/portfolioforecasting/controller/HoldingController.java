package com.portfolioforecasting.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.portfolioforecasting.dto.request.HoldingRequest;
import com.portfolioforecasting.dto.response.HoldingResponse;
import com.portfolioforecasting.service.HoldingService;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @PostMapping
    public HoldingResponse createHolding(@RequestBody HoldingRequest request) {
        return holdingService.createHolding(request);
    }

    @GetMapping
    public List<HoldingResponse> getAllHoldings() {
        return holdingService.getAllHoldings();
    }

    @GetMapping("/{id}")
    public HoldingResponse getHoldingById(@PathVariable Long id) {
        return holdingService.getHoldingById(id);
    }

    @PutMapping("/{id}")
    public HoldingResponse updateHolding(
            @PathVariable Long id,
            @RequestBody HoldingRequest request) {

        return holdingService.updateHolding(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHolding(@PathVariable Long id) {
        holdingService.deleteHolding(id);
    }
}