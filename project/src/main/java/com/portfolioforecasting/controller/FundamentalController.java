package com.portfolioforecasting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolioforecasting.dto.response.FundamentalResponse;
import com.portfolioforecasting.service.FundamentalService;

@RestController
@RequestMapping("/api/fundamentals")
public class FundamentalController {

    private final FundamentalService fundamentalService;

    public FundamentalController(FundamentalService fundamentalService) {
        this.fundamentalService = fundamentalService;
    }

    @GetMapping("/{symbol}")
    public FundamentalResponse getFundamentals(@PathVariable String symbol) {
        return fundamentalService.getFundamentals(symbol);
    }
}
