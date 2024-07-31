package com.unigpt.user.controller;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.service.BotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/bots")
public class BotController {
    private final BotService service;

    public BotController(BotService service) {
        this.service = service;
    }

    @PostMapping("/{botid}")
    public ResponseEntity<ResponseDTO> createBot(
            @RequestBody BotEditInfoDTO dto,
            @PathVariable Integer botid,
            @RequestHeader(name = "X-User-Id") Integer userId
    ) {
        try {
            return ResponseEntity.ok(service.createBot(dto, botid, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botid}")
    public ResponseEntity<Object> updateBot(
            @PathVariable Integer botid,
            @RequestBody BotEditInfoDTO dto,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.updateBot(botid, dto, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

}
