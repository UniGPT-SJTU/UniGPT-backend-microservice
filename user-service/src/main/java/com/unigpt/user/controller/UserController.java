package com.unigpt.user.controller;

import com.unigpt.user.dto.GetBotsOkResponseDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.dto.UserUpdateDTO;
import com.unigpt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String account,
            @RequestParam(defaultValue = "") String name
    ){
        try {
            return ResponseEntity.ok(service.createUser(email, account, name));
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

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(@RequestHeader(name = "X-User-Id") Integer id) {
        try {
            return ResponseEntity.ok(service.findUserById(id));
        } catch(Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{userid}")
    public ResponseEntity<Object> updateUserProfile(
            @PathVariable Integer userid,
            @RequestBody UserUpdateDTO userUpdateDTO,
            @RequestHeader(name = "X-User-Id") Integer id,
            @RequestHeader(name = "X-Is-Admin") boolean isAdmin
    ) {
        if (!isAdmin && !userid.equals(id)) {
            return ResponseEntity.status(401).body("Permission denied");
        }

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

    @GetMapping("/{userid}/used-bots")
    public ResponseEntity<Object> getUsedBots(
            @PathVariable Integer userid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        try {
            // 使用userid和token
            return ResponseEntity.ok(service.getUsedBots(userid, page, pagesize));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/used-bots/{botId}")
    public ResponseEntity<Object> useBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId
    ){
        try {
            return ResponseEntity.ok(service.useBot(botId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/used-bots/{botId}")
    public ResponseEntity<Object> disuseBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId
    ){
        try {
            return ResponseEntity.ok(service.disuseBot(botId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

}
