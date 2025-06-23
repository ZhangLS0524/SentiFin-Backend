package com.example.demo.controller;

import com.example.demo.enumeration.UserRoleEnum;
import com.example.demo.model.Announcement;
import com.example.demo.model.User;
import com.example.demo.service.AnnouncementService;
import com.example.demo.service.UserService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    
    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Announcement> getAllAnnouncements(){
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/{id}")
    public Announcement getAnnouncementById(@PathVariable Long id){
        return announcementService.getAnnouncementById(id);
    }

    @PostMapping
    public Announcement createAnnouncement(@RequestBody AnnouncementRequest announcementRequest){
        if(!userService.getUserById(announcementRequest.getUserId()).getRole().equals(UserRoleEnum.ADMIN.toString())){
            System.out.println(userService.getUserById(announcementRequest.getUserId()).getRole());
            throw new RuntimeException("User is not an admin");
        }

        if(announcementRequest.getTitle() == null || announcementRequest.getDescription() == null || announcementRequest.getAttachmentUrl() == null || announcementRequest.getUserId() == null){
            throw new RuntimeException("Invalid announcement request");
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementRequest.getTitle());
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setAttachmentUrl(announcementRequest.getAttachmentUrl());
        User user = userService.getUserById(announcementRequest.getUserId());
        announcement.setUser(user);
        announcement.setEdited(false);
        announcement.setEditAuthor(null);
        return announcementService.createAnnouncement(announcement);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAnnouncement(@PathVariable Long id, @RequestParam Long userId){
        if(!userService.getUserById(userId).getRole().equals(UserRoleEnum.ADMIN.toString())){
            throw new RuntimeException("User is not an admin");
        }

        return announcementService.deleteAnnouncementById(id);
    }

    @PutMapping("/{id}")
    public Announcement updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRequest announcementRequest){
        if(!userService.getUserById(announcementRequest.getUserId()).getRole().equals(UserRoleEnum.ADMIN.toString())){
            throw new RuntimeException("User is not an admin");
        }

        if(announcementRequest.getTitle() == null || announcementRequest.getDescription() == null || announcementRequest.getUserId() == null){
            throw new RuntimeException("Invalid announcement request");
        }

        Announcement announcement = announcementService.getAnnouncementById(id);
        announcement.setTitle(announcementRequest.getTitle());
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setAttachmentUrl(announcementRequest.getAttachmentUrl());
        User user = userService.getUserById(announcementRequest.getUserId());
        announcement.setEditAuthor(user);
        announcement.setEdited(true);

        return announcementService.updateAnnouncement(announcement);
    }

    // DTO for Announcement
    @Getter
    @Setter
    public static class AnnouncementRequest{
        @NotBlank(message = "Title is required")
        private String title;
        @NotBlank(message = "Description is required")
        private String description;
        private String attachmentUrl;
        @NotNull(message = "User ID is required")
        private Long userId;
    }
    
}
