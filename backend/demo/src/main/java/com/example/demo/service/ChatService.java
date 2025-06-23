package com.example.demo.service;

import com.example.demo.model.Chat;
import com.example.demo.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public List<Chat> getChatsByUserId(Long userId) {
        return chatRepository.findChatsByUserId(userId);
    }

    public Chat updateChat(Chat chat) {
        if (!chatRepository.existsById(chat.getId())) {
            throw new RuntimeException("Chat not found");
        }
        return chatRepository.save(chat);
    }

    public boolean deleteChatById(Long id) {
        if (!chatRepository.existsById(id)) {
            throw new RuntimeException("Chat not found");
        }
        chatRepository.deleteById(id);
        return true;
    }
}
