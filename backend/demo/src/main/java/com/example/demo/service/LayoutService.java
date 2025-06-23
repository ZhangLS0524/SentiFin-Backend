package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Layout;
import com.example.demo.repository.LayoutRepository;
import com.example.demo.repository.UserRepository;

@Service
public class LayoutService {
    
    @Autowired
    private LayoutRepository layoutRepository;

    @Autowired
    private UserRepository userRepository;

    public Layout createLayout(Layout layout){
        if(layout.getUserId() == null || layout.getLayoutName() == null || layout.getTickers() == null){
            throw new RuntimeException("Invalid layout request");
        }
        return layoutRepository.save(layout);
    }

    public List<Layout> getAllLayoutsByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        return layoutRepository.findByUserId(userId);
    }

    public Layout getLayoutById(Long id){
        if(!layoutRepository.existsById(id)){
            throw new RuntimeException("Layout not found");
        }
        return layoutRepository.findById(id).orElse(null);
    }

    public Layout updateLayout(Layout layout){
        if(!layoutRepository.existsById(layout.getId())){
            throw new RuntimeException("Layout not found");
        }
        return layoutRepository.save(layout);
    }

    public boolean deleteLayoutById(Long id){
        if(!layoutRepository.existsById(id)){
            throw new RuntimeException("Layout not found");
        }
        layoutRepository.deleteById(id);
        return true;
    }
}
