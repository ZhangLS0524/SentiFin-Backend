package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
