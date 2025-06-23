package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alert")
@Getter
@Setter
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "limit_price")
    private Double limitPrice;

    @Column(name = "direction")
    private String direction;

    @Column(name = "is_notified")
    private Boolean isNotified;
}
