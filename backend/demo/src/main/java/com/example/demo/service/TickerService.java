package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ticker;
import com.example.demo.repository.TickerRepository;

@Service
public class TickerService {
    
    @Autowired
    private TickerRepository tickerRepository;

    public Ticker createTicker(Ticker ticker) {
        return tickerRepository.save(ticker);
    }

    public Ticker getTickerBySymbol(String symbol) {
        if(!tickerRepository.existsBySymbol(symbol)){
            throw new RuntimeException("Ticker not found");
        }
        return tickerRepository.findBySymbol(symbol);
    }

    public List<Ticker> getActiveTickers() {
        return tickerRepository.findByIsActiveTrue();
    }

    public List<Ticker> getTickersBySector(String sector) {
        return tickerRepository.findBySector(sector);
    }

    public Ticker updateTicker(Ticker ticker) {
        if(!tickerRepository.existsById(ticker.getId())){
            throw new RuntimeException("Ticker not found");
        }
        return tickerRepository.save(ticker);
    }

    public boolean deleteTicker(Long id) {
        if(!tickerRepository.existsById(id)){
            throw new RuntimeException("Ticker not found");
        }
        tickerRepository.deleteById(id);
        return true;
    }

    public Ticker getTickerById(Long id) {
        if(!tickerRepository.existsById(id)){
            throw new RuntimeException("Ticker not found");
        }
        return tickerRepository.findById(id).orElse(null);
    }

    public List<Ticker> getAllTickers() {
        return tickerRepository.findAll();
    }
}
