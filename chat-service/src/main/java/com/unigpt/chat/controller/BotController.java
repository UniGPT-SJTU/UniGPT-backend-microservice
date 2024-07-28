package com.unigpt.chat.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.chat.dto.BotEditInfoDTO;
import com.unigpt.chat.dto.PromptDTO;
import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.service.BotService;

@RestController
@RequestMapping("/internal/bots")
public class BotController {

    private final BotService service;

    public BotController(BotService service) {
        this.service = service;
    }

    @GetMapping("/{botId}/histories")
    public ResponseEntity<Object> getBotHistory(
            @PathVariable Integer botId,
            @RequestParam Integer page,
            @RequestParam Integer pagesize,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.getBotHistory(userId, botId, page, pagesize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{botId}/histories")
    public ResponseEntity<Object> createBotHistory(
            @PathVariable Integer botId,
            @RequestBody List<PromptDTO> promptList,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.createBotHistory(userId, botId, promptList));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{botId}")
    public ResponseEntity<ResponseDTO> createBot(
            @PathVariable Integer botId,
            @RequestBody BotEditInfoDTO dto,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            service.createBot(userId, botId, dto);
            return ResponseEntity.ok(new ResponseDTO(true, "Bot created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botId}")
    public ResponseEntity<Object> updateBot(
            @PathVariable Integer botId,
            @RequestBody BotEditInfoDTO dto,
            @RequestHeader(name = "X-User-Id") Integer userId,
            @RequestHeader(name = "X-Is-Admin") Boolean isAdmin) {
        try {
            service.updateBot(userId, isAdmin, botId, dto);
            return ResponseEntity.ok(new ResponseDTO(true, "Bot updated successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}
