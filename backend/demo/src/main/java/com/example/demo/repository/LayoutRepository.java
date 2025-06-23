package com.example.demo.repository;

import com.example.demo.model.Layout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LayoutRepository extends JpaRepository<Layout, Long> {
    List<Layout> findByUserId(Long userId);
}
