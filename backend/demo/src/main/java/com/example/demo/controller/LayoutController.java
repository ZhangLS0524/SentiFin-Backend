package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Layout;
import com.example.demo.service.LayoutService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/layout")
public class LayoutController {
    
    @Autowired
    private LayoutService layoutService;

    @GetMapping("/user/{userId}")
    public List<Layout> getAllLayoutsByUserId(@PathVariable Long userId){
        return layoutService.getAllLayoutsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Layout getLayoutById(@PathVariable Long id){
        return layoutService.getLayoutById(id);
    }

    @PostMapping
    public Layout createLayout(@RequestBody LayoutRequest layoutRequest){
        if(layoutRequest.getUserId() == null || layoutRequest.getLayoutName() == null || layoutRequest.getTickers() == null){
            throw new RuntimeException("Invalid layout request");
        }

        Layout layout = new Layout();
        layout.setUserId(layoutRequest.getUserId());
        layout.setLayoutName(layoutRequest.getLayoutName());
        layout.setTickers(layoutRequest.getTickers());
        return layoutService.createLayout(layout);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLayoutById(@PathVariable Long id, @RequestParam Long userId){
        if(layoutService.getLayoutById(id).getUserId() != userId){
            throw new RuntimeException("User does not have permission to delete this layout");
        }
        return layoutService.deleteLayoutById(id);
    }

    @PutMapping("/{id}")
    public Layout updateLayout(@PathVariable Long id, @RequestBody LayoutRequest layoutRequest){
        if(layoutRequest.getUserId() == null || layoutRequest.getLayoutName() == null || layoutRequest.getTickers() == null){
            throw new RuntimeException("Invalid layout request");
        }

        Layout layout = layoutService.getLayoutById(id);
        layout.setUserId(layoutRequest.getUserId());
        layout.setLayoutName(layoutRequest.getLayoutName());
        layout.setTickers(layoutRequest.getTickers());
        return layoutService.updateLayout(layout);
    }

    // DTO for layout request
    @Getter
    @Setter
    public static class LayoutRequest{
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotBlank(message = "Layout name is required")
        private String layoutName;
        @NotBlank(message = "Tickers is required")
        private String tickers;
    }
}