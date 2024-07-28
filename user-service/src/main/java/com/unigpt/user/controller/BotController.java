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

    @PostMapping
    public ResponseEntity<ResponseDTO> createBot(@RequestBody BotEditInfoDTO dto) {
        try {
            return ResponseEntity.ok(service.createBot(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

}
