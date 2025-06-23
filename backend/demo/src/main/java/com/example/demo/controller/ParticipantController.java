package com.example.demo.controller;

import com.example.demo.model.Chat;
import com.example.demo.model.Participant;
import com.example.demo.model.User;
import com.example.demo.service.ChatService;
import com.example.demo.service.ParticipantService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("/chat/{chatId}")
    public List<Participant> getParticipantsByChatId(@PathVariable Long chatId) {
        return participantService.getParticipantsByChatId(chatId);
    }

    @GetMapping("/user/{userId}")
    public List<Participant> getParticipantsByUserId(@PathVariable Long userId) {
        return participantService.getParticipantsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Participant getParticipantById(@PathVariable Long id) {
        return participantService.getParticipantById(id);
    }

    @PostMapping("/chat/{chatId}/user/{userId}")
    public Participant createParticipant(@RequestBody ParticipantRequest participantRequest) {
        Participant participant = new Participant();
        Chat chat = chatService.getChatById(participantRequest.getChatId());
        User user = userService.getUserById(participantRequest.getUserId());
        participant.setChat(chat);
        participant.setUser(user);
        participant.setIsAdmin(participantRequest.getIsAdmin() == null ? false : participantRequest.getIsAdmin());
        return participantService.createParticipant(participant);
    }

    @PutMapping("/{id}")
    public Participant updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequest participantRequest) {
        Participant participant = participantService.getParticipantById(id);
        Chat chat = chatService.getChatById(participantRequest.getChatId());
        User user = userService.getUserById(participantRequest.getUserId());
        participant.setChat(chat);
        participant.setUser(user);
        participant.setIsAdmin(participantRequest.getIsAdmin());
        return participantService.updateParticipant(participant);
    }

    @DeleteMapping("/{id}")
    public boolean deleteParticipant(@PathVariable Long id) {
        return participantService.deleteParticipantById(id);
    }

    // DTO for request body
    @Getter
    @Setter
    public static class ParticipantRequest {
        @NotNull(message = "Chat ID is required")
        private Long chatId;
        @NotNull(message = "User ID is required")
        private Long userId;
        private Boolean isAdmin;
    }
}
