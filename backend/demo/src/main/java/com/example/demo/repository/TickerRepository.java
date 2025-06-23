package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Ticker;

public interface TickerRepository extends JpaRepository<Ticker, Long> {
    Ticker findBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
    List<Ticker> findByIsActiveTrue();
    List<Ticker> findBySector(String sector);
}
