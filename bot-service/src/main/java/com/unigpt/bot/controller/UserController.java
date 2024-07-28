package com.unigpt.bot.controller;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.dto.UpdateUserInfoRequestDTO;
import com.unigpt.bot.service.UserService;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(
            @PathVariable Integer id,
            @RequestBody UpdateUserInfoRequestDTO updateUserInfoRequestDTO,
            @RequestHeader(name = "X-User-Id") Integer requestUserId) {
        try {
            service.updateUserInfo(requestUserId, id, updateUserInfoRequestDTO);
            return ResponseEntity.ok(new ResponseDTO(true, "Update user info successfully"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{userid}/starred-bots")
    public ResponseEntity<Object> getStarredBots(
            @PathVariable Integer userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        try {
            // 使用userid和token
            return ResponseEntity.ok(service.getStarredBots(userid, page, pagesize));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{userid}/created-bots")
    public ResponseEntity<Object> getCreatedBots(
            @PathVariable Integer userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        try {
            // 使用userid和token
            return ResponseEntity.ok(service.getCreatedBots(userid, page, pagesize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}
