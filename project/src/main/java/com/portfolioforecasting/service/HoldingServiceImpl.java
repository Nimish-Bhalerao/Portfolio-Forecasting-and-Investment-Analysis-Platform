package com.portfolioforecasting.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.dto.request.HoldingRequest;
import com.portfolioforecasting.dto.response.HoldingResponse;
import com.portfolioforecasting.entity.Holding;
import com.portfolioforecasting.entity.Portfolio;
import com.portfolioforecasting.repository.HoldingRepository;
import com.portfolioforecasting.repository.PortfolioRepository;

@Service
public class HoldingServiceImpl implements HoldingService {
    private final HoldingRepository holdingRepository;
    private final PortfolioRepository portfolioRepository;

    public HoldingServiceImpl(HoldingRepository holdingRepository, PortfolioRepository portfolioRepository) {
        this.holdingRepository = holdingRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public HoldingResponse createHolding(HoldingRequest request) {
        Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found by id: " + request.getPortfolioId()));
        Holding holding = Holding.builder()
                .stockSymbol(request.getStockSymbol())
                .companyName(request.getCompanyName())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .portfolio(portfolio)
                .build();
        holding = holdingRepository.save(holding);

        return HoldingResponse.builder()
                .stockSymbol(holding.getStockSymbol())
                .companyName(holding.getCompanyName())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .purchaseDate(holding.getPurchaseDate())
                .build();
    }

    @Override
    public List<HoldingResponse> getAllHoldings() {
        return holdingRepository.findAll().stream()
                .map(holding -> HoldingResponse.builder()
                        .stockSymbol(holding.getStockSymbol())
                        .companyName(holding.getCompanyName())
                        .quantity(holding.getQuantity())
                        .buyPrice(holding.getBuyPrice())
                        .purchaseDate(holding.getPurchaseDate())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public HoldingResponse getHoldingById(Long id) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found by id: " + id));
        return HoldingResponse.builder()
                .stockSymbol(holding.getStockSymbol())
                .companyName(holding.getCompanyName())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .purchaseDate(holding.getPurchaseDate())
                .build();

    }

    @Override
    public HoldingResponse updateHolding(Long id, HoldingRequest holding) {
        Holding existingHolding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found by id: " + id));
        existingHolding.setStockSymbol(holding.getStockSymbol());
        existingHolding.setCompanyName(holding.getCompanyName());
        existingHolding.setQuantity(holding.getQuantity());
        existingHolding.setBuyPrice(holding.getBuyPrice());
        existingHolding.setPortfolio(portfolioRepository.findById(holding.getPortfolioId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found by id: " + holding.getPortfolioId())));
        existingHolding = holdingRepository.save(existingHolding);
        return HoldingResponse.builder()
                .stockSymbol(existingHolding.getStockSymbol())
                .companyName(existingHolding.getCompanyName())
                .quantity(existingHolding.getQuantity())
                .buyPrice(existingHolding.getBuyPrice())
                .purchaseDate(existingHolding.getPurchaseDate())
                .build();

    }

    @Override
    public void deleteHolding(Long id) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found with id : " + id));

        holdingRepository.delete(holding);
    }

}
