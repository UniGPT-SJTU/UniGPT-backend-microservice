package com.unigpt.plugin.controller;


import com.unigpt.plugin.dto.*;
import com.unigpt.plugin.service.BotService;
import com.unigpt.plugin.service.PluginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
}