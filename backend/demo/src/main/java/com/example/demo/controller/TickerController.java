package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ticker;
import com.example.demo.service.TickerService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/tickers")
public class TickerController {
    
    @Autowired
    private TickerService tickerService;

    @GetMapping("/{id}")
    public Ticker getTickerById(@PathVariable Long id){
        return tickerService.getTickerById(id);
    }

    @GetMapping("/symbol/{symbol}")
    public Ticker getTickerBySymbol(@PathVariable String symbol){
        return tickerService.getTickerBySymbol(symbol);
    }

    @GetMapping("/active")
    public List<Ticker> getActiveTickers(){
        return tickerService.getActiveTickers();
    }

    @GetMapping("/sector/{sector}")
    public List<Ticker> getTickersBySector(@PathVariable String sector){
        return tickerService.getTickersBySector(sector);
    }

    @PostMapping
    public Ticker createTicker(@RequestBody TickerRequest tickerRequest){
        if(tickerRequest.getSymbol() == null || tickerRequest.getName() == null || tickerRequest.getSector() == null || tickerRequest.getIsActive() == null){
            throw new RuntimeException("Invalid ticker request");
        }

        Ticker ticker = new Ticker();
        ticker.setSymbol(tickerRequest.getSymbol());
        ticker.setName(tickerRequest.getName());
        ticker.setSector(tickerRequest.getSector());
        ticker.setIsActive(tickerRequest.getIsActive());
        return tickerService.createTicker(ticker);
    }

    @PutMapping("/{id}")
    public Ticker updateTicker(@PathVariable Long id, @RequestBody TickerRequest tickerRequest){
        if(tickerRequest.getSymbol() == null || tickerRequest.getName() == null || tickerRequest.getSector() == null || tickerRequest.getIsActive() == null){
            throw new RuntimeException("Invalid ticker request");
        }

        Ticker ticker = tickerService.getTickerById(id);
        ticker.setSymbol(tickerRequest.getSymbol());
        ticker.setName(tickerRequest.getName());
        ticker.setSector(tickerRequest.getSector());
        ticker.setIsActive(tickerRequest.getIsActive());
        return tickerService.updateTicker(ticker);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTicker(@PathVariable Long id){
        return tickerService.deleteTicker(id);
    }

    @GetMapping
    public List<Ticker> getAllTickers(){
        return tickerService.getAllTickers();
    }
    
    @Getter
    @Setter
    public static class TickerRequest{
        @NotBlank(message = "Symbol is required")
        private String symbol;
        @NotBlank(message = "Name is required")
        private String name;
        @NotBlank(message = "Sector is required")
        private String sector;
        @NotNull(message = "Is active is required")
        private Boolean isActive;
    }
}
