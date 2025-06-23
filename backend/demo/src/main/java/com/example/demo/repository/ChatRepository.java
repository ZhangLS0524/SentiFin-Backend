package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT DISTINCT c FROM Chat c JOIN c.participant p WHERE p.user.id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT c FROM Chat c JOIN c.participant p WHERE p.user.id = :userId AND c.id = :chatId")
    Chat findChatByUserIdAndChatId(@Param("userId") Long userId, @Param("chatId") Long chatId);
}
