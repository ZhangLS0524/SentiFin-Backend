package com.example.demo.controller;

import com.example.demo.enumeration.UserRoleEnum;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
        User user = new User();

        if (userRequest.getUsername() == null || userRequest.getEmail() == null || userRequest.getPassword() == null) {
            throw new RuntimeException("Invalid user request");
        }
        
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAltEmail(userRequest.getAltEmail());
        user.setProfilePicture(userRequest.getProfilePicture());
        user.setRole(UserRoleEnum.USER.toString());
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id, @RequestParam Long userId) {
        if(userService.getUserById(userId).getRole() != UserRoleEnum.ADMIN.toString()){
            throw new RuntimeException("User does not have permission to delete this user");
        }
        return userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User user = userService.getUserById(id);
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAltEmail(userRequest.getAltEmail());
        user.setProfilePicture(userRequest.getProfilePicture());
        return userService.updateUser(user);
    }

    // DTO for request body
    @Getter
    @Setter
    public static class UserRequest {
        private String username;
        private String email;
        private String password;
        private String phoneNumber;
        private String altEmail;
        private String profilePicture;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}