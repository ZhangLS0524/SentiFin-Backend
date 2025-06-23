package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Watchlist;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WatchlistRepository;

@Service
public class WatchlistService {
    
    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    public Watchlist createWatchlist(Watchlist watchlist){
        if(!userRepository.existsById(watchlist.getUserId())){
            throw new RuntimeException("User not found");
        }
        
        if(watchlist.getUserId() == null || watchlist.getTickers() == null){
            throw new RuntimeException("Invalid watchlist request");
        }
        
        return watchlistRepository.save(watchlist);
    }

    public Watchlist getWatchlistsByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        return watchlistRepository.findByUserId(userId);
    }
    
    public Watchlist getWatchlistById(Long id){
        if(!watchlistRepository.existsById(id)){
            throw new RuntimeException("Watchlist not found");
        }
        return watchlistRepository.findById(id).orElse(null);
    }

    public Watchlist updateWatchlist(Watchlist watchlist){
        if(!watchlistRepository.existsById(watchlist.getId())){
            throw new RuntimeException("Watchlist not found");
        }
        return watchlistRepository.save(watchlist);
    }

    public boolean deleteWatchlistById(Long id){
        if(!watchlistRepository.existsById(id)){
            throw new RuntimeException("Watchlist not found");
        }
        watchlistRepository.deleteById(id);
        return true;
    }
}
