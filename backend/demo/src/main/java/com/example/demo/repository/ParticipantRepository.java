package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByChatId(Long chatId);
    List<Participant> findByUserId(Long userId);
}
