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
@Table(name = "layout")
@Getter
@Setter
public class Layout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "layout_name")
    private String layoutName;

    @Column(name = "tickers", columnDefinition = "TEXT")
    private String tickers; 
}
