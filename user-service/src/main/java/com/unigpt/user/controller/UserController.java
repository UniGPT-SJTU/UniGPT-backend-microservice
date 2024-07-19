package com.unigpt.user.controller;

import com.unigpt.user.dto.UserUpdateDTO;
import com.unigpt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestParam(defaultValue = "") String email){
        try {
            return ResponseEntity.ok(service.createUser(email));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUserProfile(@PathVariable Integer userid) {
        try {
            return ResponseEntity.ok(service.findUserById(userid));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{userid}")
    public ResponseEntity<Object> updateUserProfile(
            @PathVariable Integer userid,
            @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        try {
            service.updateUserInfo(userid, userUpdateDTO);
            return ResponseEntity.ok("User info updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize,
            @RequestParam(defaultValue = "keyword") String type,
            @RequestParam(defaultValue = "", required = false) String q) {
        try {
            return ResponseEntity.ok(service.getUsers(page, pagesize, type, q));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
