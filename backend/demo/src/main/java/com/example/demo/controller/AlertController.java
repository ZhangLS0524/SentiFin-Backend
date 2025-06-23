package com.example.demo.controller;

import com.example.demo.enumeration.DirectionEnum;
import com.example.demo.model.Alert;
import com.example.demo.service.AlertService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    
    @Autowired
    private AlertService alertService;

    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable Long id){
        return alertService.getAlertById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Alert> getAlertsByUserId(@PathVariable Long userId){
        return alertService.getAlertsByUserId(userId);
    }

    @PostMapping
    public Alert createAlert(@RequestBody AlertRequest alertRequest){
        if(alertRequest.getTicker() == null || alertRequest.getUserId() == null || alertRequest.getLimitPrice() == null || alertRequest.getDirection() == null){
            throw new RuntimeException("Invalid alert request");
        };
        
        Alert alert = new Alert();
        alert.setTicker(alertRequest.getTicker());
        alert.setUserId(alertRequest.getUserId());
        alert.setLimitPrice(alertRequest.getLimitPrice());
        alert.setDirection(alertRequest.getDirection().toString());
        alert.setIsNotified(alertRequest.getIsNotified() != null ? alertRequest.getIsNotified() : false);
        return alertService.createAlert(alert);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAlertById(@PathVariable Long id){
        return alertService.deleteAlertById(id);
    }

    @PutMapping("/{id}")
    public Alert updateAlert(@PathVariable Long id, @RequestBody AlertRequest alertRequest){
        if(alertRequest.getTicker() == null || alertRequest.getUserId() == null || alertRequest.getLimitPrice() == null || alertRequest.getDirection() == null){
            throw new RuntimeException("Invalid alert request");
        };

        Alert alert = alertService.getAlertById(id);
        alert.setTicker(alertRequest.getTicker());
        alert.setUserId(alertRequest.getUserId());
        alert.setLimitPrice(alertRequest.getLimitPrice());
        alert.setDirection(alertRequest.getDirection().toString());
        alert.setIsNotified(alertRequest.getIsNotified() != null ? alertRequest.getIsNotified() : alert.getIsNotified());
        return alertService.updateAlert(alert);
    }

    @GetMapping("/notified")
    public List<Alert> getNotifiedAlerts(){
        return alertService.getAlertsByIsNotified(true);
    }

    @PutMapping("/{id}/reset")
    public Alert resetAlert(@PathVariable Long id){
        return alertService.resetAlert(id);
    }

    // DTO for request body
    @Getter
    @Setter
    public static class AlertRequest {
        @NotBlank(message = "Ticker is required")
        private String ticker;
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotNull(message = "Limit price is required")
        private Double limitPrice;
        @NotNull(message = "Direction is required")
        private DirectionEnum direction;
        private Boolean isNotified;
    }
}