package com.unigpt.bot.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unigpt.bot.dto.BotEditInfoDTO;
import com.unigpt.bot.dto.CommentRequestDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.service.BotService;

@RestController
@RequestMapping("/internal/bots")
public class BotController {

    private final BotService service;

    public BotController(BotService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getBots(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "latest") String order,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pagesize) {
        try {
            return ResponseEntity.ok(service.getBots(q, order, page, pagesize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBotProfile(
            @PathVariable Integer id,
            @RequestParam String info,
            @RequestHeader(name = "X-User-Id", defaultValue = "0") Integer userId,
            @RequestHeader(name = "X-Is-Admin", defaultValue = "false") Boolean isAdmin) {
        try {
            return switch (info) {
                case "brief" -> ResponseEntity.ok(service.getBotBriefInfo(userId, isAdmin, id));
                case "detail" -> ResponseEntity.ok(service.getBotDetailInfo(userId, isAdmin, id));
                case "edit" -> ResponseEntity.ok(service.getBotEditInfo(userId, isAdmin, id));
                case "history" -> ResponseEntity.ok(service.getBotHistoryInfo(id));
                default -> ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(false, "Invalid info parameter"));
            };
        } catch (Exception e) {
            System.out.println("In getBotProfile" + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createBot(
            @RequestBody BotEditInfoDTO dto,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.createBot(userId, dto));
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
            return ResponseEntity.ok(service.updateBot(userId, isAdmin, botId, dto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botId}/likes")
    public ResponseEntity<Object> likeBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.likeBot(userId, botId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{botId}/likes")
    public ResponseEntity<Object> dislikeBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.dislikeBot(userId, botId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{botId}/stars")
    public ResponseEntity<Object> starBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.starBot(userId, botId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{botId}/stars")
    public ResponseEntity<Object> unstarBot(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.unstarBot(userId, botId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{botid}/comments")
    public ResponseEntity<Object> getComments(@PathVariable Integer botid,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pagesize) {
        try {
            return ResponseEntity.ok(service.getComments(botid, page, pagesize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/{botid}/comments")
    public ResponseEntity<Object> createComment(
            @PathVariable Integer botid,
            @RequestBody CommentRequestDTO request,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.createComment(userId, botid, request.getContent()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

}
