package com.portfolioforecasting.service;

import java.util.List;

import com.portfolioforecasting.entity.Portfolio;

public interface PortfolioService {

    Portfolio createPortfolio(Portfolio portfolio);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(Long id);

    Portfolio updatePortfolio(Long id, Portfolio portfolio);

    void deletePortfolio(Long id);

}
