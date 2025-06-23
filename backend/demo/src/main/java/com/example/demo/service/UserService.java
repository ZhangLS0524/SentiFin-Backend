package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // CONSIDERATION: add a builder method to create a user with default USER role. 
    public User createUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
    }

    public boolean deleteUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(User user){
        if(!userRepository.existsById(user.getId())){
            throw new RuntimeException("User not found");
        }
        return userRepository.save(user);
    }
}