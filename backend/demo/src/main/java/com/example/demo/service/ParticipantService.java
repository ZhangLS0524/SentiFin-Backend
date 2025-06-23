package com.example.demo.service;

import com.example.demo.model.Participant;
import com.example.demo.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public List<Participant> getParticipantsByChatId(Long chatId) {
        return participantRepository.findByChatId(chatId);
    }

    public List<Participant> getParticipantsByUserId(Long userId) {
        return participantRepository.findByUserId(userId);
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
    }

    public Participant updateParticipant(Participant participant) {
        if (!participantRepository.existsById(participant.getId())) {
            throw new RuntimeException("Participant not found");
        }
        return participantRepository.save(participant);
    }

    public boolean deleteParticipantById(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new RuntimeException("Participant not found");
        }
        participantRepository.deleteById(id);
        return true;
    }
}
