package com.portfolioforecasting.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.dto.request.HoldingRequest;
import com.portfolioforecasting.dto.response.HoldingResponse;
import com.portfolioforecasting.entity.Holding;
import com.portfolioforecasting.entity.Portfolio;
import com.portfolioforecasting.repository.HoldingRepository;
import com.portfolioforecasting.repository.PortfolioRepository;
import com.portfolioforecasting.service.HoldingService;

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
        return mapToResponse(holding);
    }

    @Override
    public List<HoldingResponse> getAllHoldings() {
        return holdingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HoldingResponse getHoldingById(Long id) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found by id: " + id));
        return mapToResponse(holding);
    }

    @Override
    public HoldingResponse updateHolding(Long id, HoldingRequest request) {
        Holding existingHolding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found by id: " + id));

        existingHolding.setStockSymbol(request.getStockSymbol());
        existingHolding.setCompanyName(request.getCompanyName());
        existingHolding.setQuantity(request.getQuantity());
        existingHolding.setBuyPrice(request.getBuyPrice());

        if (request.getPortfolioId() != null) {
            Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                    .orElseThrow(() -> new RuntimeException("Portfolio not found by id: " + request.getPortfolioId()));
            existingHolding.setPortfolio(portfolio);
        }

        existingHolding = holdingRepository.save(existingHolding);
        return mapToResponse(existingHolding);
    }

    @Override
    public void deleteHolding(Long id) {
        Holding holding = holdingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found with id : " + id));
        holdingRepository.delete(holding);
    }

    private HoldingResponse mapToResponse(Holding holding) {
        return HoldingResponse.builder()
                .id(holding.getId())
                .stockSymbol(holding.getStockSymbol())
                .companyName(holding.getCompanyName())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .purchaseDate(holding.getPurchaseDate())
                .build();
    }
}
