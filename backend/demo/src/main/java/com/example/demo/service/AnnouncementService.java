package com.example.demo.service;

import com.example.demo.model.Announcement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.AnnouncementRepository;

@Service
public class AnnouncementService {
    
    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(Announcement announcement){
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAll();
    }

    public Announcement getAnnouncementById(Long id){
        if(!announcementRepository.existsById(id)){
            throw new RuntimeException("Announcement not found");
        }
        return announcementRepository.findById(id).orElse(null);
    }

    public Announcement updateAnnouncement(Announcement announcement){
        if(!announcementRepository.existsById(announcement.getId())){
            throw new RuntimeException("Announcement not found");
        }
        return announcementRepository.save(announcement);
    }

    public boolean deleteAnnouncementById(Long id){
        if(!announcementRepository.existsById(id)){
            return false;
        }
        announcementRepository.deleteById(id);
        return true;
    }
}
