package com.example.demo.controller;

import com.example.demo.model.Chat;
import com.example.demo.model.User;
import com.example.demo.model.Message;
import com.example.demo.service.ChatService;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("/chat/{chatId}")
    public List<Message> getMessagesByChatId(@PathVariable Long chatId) {
        return messageService.getAllMessagesByChatId(chatId);
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping("/chat/{chatId}/user/{userId}")
    public Message createMessage(@PathVariable Long chatId, @PathVariable Long userId, @RequestBody MessageRequest messageRequest) {
        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setAttachment(messageRequest.getAttachment());
        Chat chat = chatService.getChatById(chatId);
        User sender = userService.getUserById(userId);
        message.setChat(chat);
        message.setSender(sender);
        return messageService.createMessage(message);
    }

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable Long id, @RequestBody MessageRequest messageRequest) {
        Message message = messageService.getMessageById(id);
        message.setContent(messageRequest.getContent());
        message.setAttachment(messageRequest.getAttachment());
        Chat chat = chatService.getChatById(messageRequest.getChatId());
        User sender = userService.getUserById(messageRequest.getSenderId());
        message.setChat(chat);
        message.setSender(sender);
        return messageService.updateMessage(message);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMessage(@PathVariable Long id) {
        return messageService.deleteMessageById(id);
    }

    // DTO for request body
    @Getter
    @Setter
    public static class MessageRequest {
        @NotNull(message = "Chat ID is required")
        private Long chatId;
        @NotNull(message = "Sender ID is required")
        private Long senderId;
        @NotBlank(message = "Content is required")
        private String content;
        private String attachment;
    }
}
