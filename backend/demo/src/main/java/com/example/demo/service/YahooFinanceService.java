package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class YahooFinanceService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getCurrentPrice(String symbol){
        String url = "http://localhost:8000/api/StockInfo/" + symbol;
        String response = restTemplate.getForObject(url, String.class);
        if(response == null){
            throw new RuntimeException("Failed to fetch stock info");
        }
        JSONObject jsonObject = new JSONObject(response);
        if (!jsonObject.has("metrics")) {
            throw new RuntimeException("metrics not found in response for ticker: " + symbol);
        }
        JSONObject metrics = jsonObject.getJSONObject("metrics");
        if (!metrics.has("current_price")) {
            throw new RuntimeException("current_price not found in metrics for ticker: " + symbol);
        }
        double currentPrice = metrics.getDouble("current_price");
        return currentPrice;
    }
}
