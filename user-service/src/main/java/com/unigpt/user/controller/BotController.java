package com.unigpt.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.service.BotService;

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
            @PathVariable Integer botid) {
        try {
            return ResponseEntity.ok(service.createBot(dto, botid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botid}")
    public ResponseEntity<Object> updateBot(
            @PathVariable Integer botid,
            @RequestBody BotEditInfoDTO dto) {
        try {
            return ResponseEntity.ok(service.updateBot(botid, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

}
