package com.portfolioforecasting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolioforecasting.entity.Holding;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

}
