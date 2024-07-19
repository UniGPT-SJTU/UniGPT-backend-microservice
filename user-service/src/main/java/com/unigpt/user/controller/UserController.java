package com.unigpt.user.controller;

import com.unigpt.user.dto.UserUpdateDTO;
import com.unigpt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
