package com.unigpt.user.controller;

import com.unigpt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUserProfile(@PathVariable Integer userid) {
        try {
            return ResponseEntity.ok(service.findUserById(userid));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
