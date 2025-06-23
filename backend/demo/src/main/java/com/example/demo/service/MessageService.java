package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.ChatRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ChatRepository chatRepository;

    public Message createMessage(Message message) {
        if(message.getContent() == null || message.getSender() == null){
            throw new RuntimeException("Invalid message request");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessagesByChatId(Long chatId) {
        if(!chatRepository.existsById(chatId)){
            throw new RuntimeException("Chat not found");
        }
        return messageRepository.findByChatId(chatId);
    }

    public Message getMessageById(Long id) {
        if(!messageRepository.existsById(id)){
            throw new RuntimeException("Message not found");
        }
        return messageRepository.findById(id).orElse(null);
    }

    public Message updateMessage(Message message) {
        if(!messageRepository.existsById(message.getId())){
            throw new RuntimeException("Message not found");
        }
        return messageRepository.save(message);
    }

    public boolean deleteMessageById(Long id) {
        if(!messageRepository.existsById(id)){
            throw new RuntimeException("Message not found");
        }
        messageRepository.deleteById(id);
        return true;
    }
}
