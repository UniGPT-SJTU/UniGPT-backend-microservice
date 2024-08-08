package com.unigpt.plugin.controller;

import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userid}")
    public ResponseEntity<ResponseDTO> createUser(
            @PathVariable Integer userid,
            @RequestParam String name
    ) {
        try{
            return ResponseEntity.ok(userService.createUser(userid, name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, e.getMessage()));
        }
    }
}
