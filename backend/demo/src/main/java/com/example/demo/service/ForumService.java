package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Forum;
import com.example.demo.repository.ForumRepository;

@Service
public class ForumService {
    
    @Autowired
    private ForumRepository forumRepository;

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public Forum getForumById(Long id) {
        if(!forumRepository.existsById(id)){
            throw new RuntimeException("Forum not found");
        }
        return forumRepository.findById(id).orElse(null);
    }

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Forum updateForum(Forum forum) {
        if (!forumRepository.existsById(forum.getId())){
            throw new RuntimeException("Forum not found");
        }
        return forumRepository.save(forum);
    }

    public boolean deleteForumById(Long id) {
        if (!forumRepository.existsById(id)){
            throw new RuntimeException("Forum not found");
        }
        forumRepository.deleteById(id);
        return true;
    }
}
