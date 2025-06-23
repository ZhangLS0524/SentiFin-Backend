package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.ForumRepository;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import lombok.Getter;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment createComment(@RequestBody CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setForum(forumRepository.findById(commentRequest.getForumId()).orElseThrow(() -> new RuntimeException("Forum not found")));
        comment.setContent(commentRequest.getContent());
        User author = userService.getUserById(commentRequest.getAuthor());
        comment.setAuthor(author);
        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        Comment existingComment = commentService.getCommentById(id);
        existingComment.setForum(forumRepository.findById(commentRequest.getForumId())
            .orElseThrow(() -> new RuntimeException("Forum not found")));
        existingComment.setContent(commentRequest.getContent());
        User author = userService.getUserById(commentRequest.getAuthor());
        existingComment.setAuthor(author);
        return commentService.updateComment(existingComment);
    }

    @DeleteMapping("/{id}")
    public boolean deleteComment(@PathVariable Long id) {
        return commentService.deleteCommentById(id);
    }

    // DTO for Comment Request
    @Getter
    @Setter
    public static class CommentRequest {
        @NotNull(message = "Forum ID is required")
        private Long forumId;
        @NotNull(message = "Content is required")
        private String content;
        @NotNull(message = "Author is required")
        private Long author;
    }
}
