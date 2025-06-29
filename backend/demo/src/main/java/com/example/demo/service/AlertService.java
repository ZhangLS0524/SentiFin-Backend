package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.Alert;
import com.example.demo.model.User;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AlertService {
    
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private YahooFinanceService yahooFinanceService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAlertsByUserId(Long userId) {
        if(!userRepository.findById(userId).isPresent()){
            throw new RuntimeException("User not found");
        }
        return alertRepository.findByUserId(userId);
    }

    public Alert getAlertById(Long id){
        if(!alertRepository.existsById(id)){
            throw new RuntimeException("Alert not found");
        }
        return alertRepository.findById(id).orElse(null);
    }

    public boolean deleteAlertById(Long id) {
        if(!alertRepository.existsById(id)){
            throw new RuntimeException("Alert not found");
        }
        alertRepository.deleteById(id);
        return true;
    }

    public Alert updateAlert(Alert alert) {
        if (!alertRepository.existsById(alert.getId())){
            throw new RuntimeException("Alert not found");
        }
        return alertRepository.save(alert);
    }

    public List<Alert> getAlertsByIsNotified(Boolean isNotified){
        return alertRepository.findByIsNotified(isNotified);
    }

    public Alert resetAlert(Long id){
        Alert alert = getAlertById(id);
        alert.setIsNotified(false);
        return updateAlert(alert);
    }

    @Scheduled(fixedRate = 900000)
    public void checkAlerts() {
        List<Alert> alerts = alertRepository.findByIsNotified(false);
        for (Alert alert : alerts) {
            double currentPrice = yahooFinanceService.getCurrentPrice(alert.getTicker());
            boolean shouldNotify = false;
            if ("UP".equals(alert.getDirection()) && currentPrice >= alert.getLimitPrice()) {
                shouldNotify = true;
            } else if ("DOWN".equals(alert.getDirection()) && currentPrice <= alert.getLimitPrice()) {
                shouldNotify = true;
            }
            if (shouldNotify) {
                alert.setIsNotified(true);
                updateAlert(alert);
                sendNotification(alert);
            }
        }
    }

    public void sendNotification(Alert alert){
        User user = userService.getUserById(alert.getUserId());
        double currentPrice = yahooFinanceService.getCurrentPrice(alert.getTicker());
        String subject = "SentiFin Price Alert: " + alert.getTicker();
        String dashboardUrl = "http://localhost:3000/dashboard?ticker=" + alert.getTicker();
        String html = "<!DOCTYPE html>"
            + "<html>"
            + "  <body style='font-family: Arial, sans-serif; background: #f7fafc; padding: 24px;'>"
            + "    <div style='max-width: 480px; margin: auto; background: #fff; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); padding: 32px;'>"
            + "      <h2 style='color: #2563eb; margin-top: 0;'>📈 Price Alert Triggered!</h2>"
            + "      <p>Hi <b>" + user.getUsername() + "</b>,</p>"
            + "      <p>"
            + "        Your alert for <b>" + alert.getTicker() + "</b> has been triggered.<br/>"
            + "        <b>Direction:</b> " + alert.getDirection() + "<br/>"
            + "        <b>Threshold:</b> " + alert.getLimitPrice() + "<br/>"
            + "        <b>Current Price:</b> " + currentPrice + ""
            + "      </p>"
            + "      <p style='margin: 24px 0;'>"
            + "        <a href='" + dashboardUrl + "' style='background: #2563eb; color: #fff; padding: 12px 24px; border-radius: 8px; text-decoration: none; font-weight: bold;'>"
            + "          View on Dashboard"
            + "        </a>"
            + "      </p>"
            + "      <p style='color: #64748b; font-size: 0.95em;'>"
            + "        You will not receive further notifications for this alert unless you reset it.<br/>"
            + "        Thank you for using SentiFin!"
            + "      </p>"
            + "    </div>"
            + "  </body>"
            + "</html>";
        emailService.sendHtmlEmail(user.getEmail(), subject, html);
    }
}
