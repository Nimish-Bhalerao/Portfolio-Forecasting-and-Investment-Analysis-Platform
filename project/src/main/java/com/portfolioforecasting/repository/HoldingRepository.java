package com.portfolioforecasting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolioforecasting.entity.Holding;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByPortfolioId(Long portfolioId);

}
