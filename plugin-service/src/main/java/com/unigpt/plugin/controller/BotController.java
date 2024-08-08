package com.unigpt.plugin.controller;


import com.unigpt.plugin.dto.*;
import com.unigpt.plugin.service.BotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/internal/bots")
public class BotController {
    BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping("/{botid}")
    public ResponseEntity<ResponseDTO> createBot(
            @RequestBody BotInfoDTO dto,
            @PathVariable Integer botid) {
        try {
            return ResponseEntity.ok(botService.createBot(dto, botid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botid}")
    public ResponseEntity<ResponseDTO> updateBot(
            @RequestBody BotInfoDTO dto,
            @PathVariable Integer botid) {
        try {
            return ResponseEntity.ok(botService.updateBot(dto, botid));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}