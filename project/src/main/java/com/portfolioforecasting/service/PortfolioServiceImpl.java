package com.portfolioforecasting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portfolioforecasting.entity.Portfolio;
import com.portfolioforecasting.repository.PortfolioRepository;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));
    }

    @Override
    public Portfolio updatePortfolio(Long id, Portfolio portfolio) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        if (portfolio.getPortfolioName() != null) {
            existingPortfolio.setPortfolioName(portfolio.getPortfolioName());
        }

        if (portfolio.getUser() != null) {
            existingPortfolio.setUser(portfolio.getUser());
        }

        if (portfolio.getCreatedAt() != null) {
            existingPortfolio.setCreatedAt(portfolio.getCreatedAt());
        }

        if (portfolio.getHoldings() != null) {
            existingPortfolio.setHoldings(portfolio.getHoldings());
        }

        return portfolioRepository.save(existingPortfolio);
    }

    @Override
    public void deletePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        portfolioRepository.delete(portfolio);
    }

}
