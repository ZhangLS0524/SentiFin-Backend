package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Comment;
import com.example.demo.model.Forum;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.ForumService;
import com.example.demo.service.UserService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/forum")
public class ForumController {
    
    @Autowired
    private ForumService forumService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Forum> getAllForums(){
        return forumService.getAllForums();
    }

    @GetMapping("/{id}")
    public Forum getForumById(@PathVariable Long id){
        return forumService.getForumById(id);
    }

    @PostMapping
    public Forum createForum(@RequestBody ForumRequest forumRequest){
        System.out.println(forumRequest.getTitle());
        System.out.println(forumRequest.getDescription());
        System.out.println(forumRequest.getAuthor());
        System.out.println(forumRequest.getAttachment());
        if(forumRequest.getTitle() == null || forumRequest.getDescription() == null || forumRequest.getAuthor() == null){
            throw new RuntimeException("Invalid forum request");
        }

        Forum forum = new Forum();
        forum.setTitle(forumRequest.getTitle());
        forum.setDescription(forumRequest.getDescription());
        forum.setAttachment(forumRequest.getAttachment());
        User author = userService.getUserById(forumRequest.getAuthor());
        forum.setAuthor(author);
        return forumService.createForum(forum);
    }

    @DeleteMapping("/{id}")
    public boolean deleteForumById(@PathVariable Long id){
        return forumService.deleteForumById(id);
    }

    @PutMapping("/{id}")
    public Forum updateForum(@PathVariable Long id, @RequestBody ForumRequest forumRequest){
        if(forumRequest.getTitle() == null || forumRequest.getDescription() == null || forumRequest.getAuthor() == null){
            throw new RuntimeException("Invalid forum request");
        }

        Forum forum = forumService.getForumById(id);
        forum.setTitle(forumRequest.getTitle());
        forum.setDescription(forumRequest.getDescription());
        forum.setAttachment(forumRequest.getAttachment());
        User author = userService.getUserById(forumRequest.getAuthor());
        forum.setAuthor(author);  
        return forumService.updateForum(forum);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByForumId(@PathVariable Long id){
        return commentService.getCommentsByForumId(id);
    }

    // DTO for Forum Request
    @Getter
    @Setter
    public static class ForumRequest {
        @NotBlank(message = "Title is required")
        private String title;
        @NotBlank(message = "Description is required")
        private String description;
        private String attachment;
        @NotNull(message = "Author is required")
        private Long author;
    }
}