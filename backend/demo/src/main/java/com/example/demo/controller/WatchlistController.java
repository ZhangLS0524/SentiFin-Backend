package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Watchlist;
import com.example.demo.service.WatchlistService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    
    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/{id}")
    public Watchlist getWatchlistById(@PathVariable Long id){
        return watchlistService.getWatchlistById(id);
    }

    @PostMapping
    public Watchlist createWatchlist(@RequestBody WatchlistRequest watchlistRequest){
        if(watchlistRequest.getUserId() == null || watchlistRequest.getTickers() == null){
            throw new RuntimeException("Invalid watchlist request");
        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUserId(watchlistRequest.getUserId());
        watchlist.setTickers(watchlistRequest.getTickers());
        return watchlistService.createWatchlist(watchlist);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWatchlistById(@PathVariable Long id, @RequestParam Long userId){
        if(watchlistService.getWatchlistById(id).getUserId() != userId){
            throw new RuntimeException("User does not have permission to delete this watchlist");
        }
        return watchlistService.deleteWatchlistById(id);
    }

    @PutMapping("/{id}")
    public Watchlist updateWatchlist(@PathVariable Long id, @RequestBody WatchlistRequest watchlistRequest){
        if(watchlistRequest.getUserId() == null || watchlistRequest.getTickers() == null){
            throw new RuntimeException("Invalid watchlist request");
        }

        Watchlist watchlist = watchlistService.getWatchlistById(id);
        watchlist.setUserId(watchlistRequest.getUserId());
        watchlist.setTickers(watchlistRequest.getTickers());
        return watchlistService.updateWatchlist(watchlist);
    }

    @GetMapping("/user/{userId}")
    public Watchlist getWatchlistsByUserId(@PathVariable Long userId){
        return watchlistService.getWatchlistsByUserId(userId);
    }

    // DTO for watchlist request
    @Getter
    @Setter
    public static class WatchlistRequest {
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotBlank(message = "Tickers is required")
        private String tickers;
    }
}
