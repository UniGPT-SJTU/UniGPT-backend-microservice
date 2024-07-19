package com.unigpt.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUserProfile(@PathVariable Integer userid) {
        return null;
    }
}
