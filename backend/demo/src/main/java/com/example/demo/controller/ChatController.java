package com.example.demo.controller;

import com.example.demo.enumeration.ChatEnum;
import com.example.demo.model.Chat;
import com.example.demo.service.ChatService;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    @GetMapping("/{id}")
    public Chat getChatById(@PathVariable Long id) {
        return chatService.getChatById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Chat> getChatsByUserId(@PathVariable Long userId) {
        return chatService.getChatsByUserId(userId);
    }

    @PostMapping
    public Chat createChat(@RequestBody ChatRequest chatRequest) {
        Chat chat = new Chat();
        chat.setChatName(chatRequest.getChatName());
        chat.setChatType(chatRequest.getChatType().toString());
        return chatService.createChat(chat);
    }

    @PutMapping("/{id}")
    public Chat updateChat(@PathVariable Long id, @RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.getChatById(id);
        chat.setChatName(chatRequest.getChatName());
        chat.setChatType(chatRequest.getChatType().toString());
        return chatService.updateChat(chat);
    }

    @DeleteMapping("/{id}")
    public boolean deleteChat(@PathVariable Long id) {
        return chatService.deleteChatById(id);
    }

    @Getter
    @Setter
    public static class ChatRequest {
        @NotBlank(message = "Chat name is required")
        private String chatName;
        @NotBlank(message = "Chat type is required")
        private ChatEnum chatType;
    }
}

