package com.unigpt.chat.controller;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.dto.UpdateUserInfoRequestDTO;
import com.unigpt.chat.service.UserService;

@RestController
@RequestMapping("/internal/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> createUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserInfoRequestDTO updateUserInfoRequestDTO) {
        try {
            service.createUser(id, updateUserInfoRequestDTO);
            return ResponseEntity.ok(new ResponseDTO(true, "Create user successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(
            @PathVariable Integer id,
            @RequestBody UpdateUserInfoRequestDTO updateUserInfoRequestDTO) {
        try {
            service.updateUserInfo(id, updateUserInfoRequestDTO);
            return ResponseEntity.ok(new ResponseDTO(true, "Update user info successfully"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}
