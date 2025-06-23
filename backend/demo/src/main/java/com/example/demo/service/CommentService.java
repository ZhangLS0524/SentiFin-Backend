package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ForumRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ForumRepository forumRepository;

    public Comment createComment(Comment comment) {
        if(comment.getContent() == null || comment.getAuthor() == null){
            throw new RuntimeException("Invalid comment request");
        }
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsByForumId(Long forumId) {
        if(!forumRepository.existsById(forumId)){
            throw new RuntimeException("Forum not found");
        }
        return commentRepository.findByForumId(forumId);
    }

    public Comment getCommentById(Long id) {
        if(!commentRepository.existsById(id)){
            throw new RuntimeException("Comment not found");
        }
        return commentRepository.findById(id).orElse(null);
    }

    public Comment updateComment(Comment comment) {
        if(!commentRepository.existsById(comment.getId())){
            throw new RuntimeException("Comment not found");
        }
        return commentRepository.save(comment);
    }

    public boolean deleteCommentById(Long id) {
        if(!commentRepository.existsById(id)){
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
        return true;
    }

    public List<Comment> getCommentsByForumId(Long forumId) {
        if(!forumRepository.existsById(forumId)){
            throw new RuntimeException("Forum not found");
        }
        return commentRepository.findByForumId(forumId);
    }
}
