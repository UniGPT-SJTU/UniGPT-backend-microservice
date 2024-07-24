package com.unigpt.bot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.service.BotService;

import java.util.List;
import java.util.NoSuchElementException;

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
            @RequestHeader(name = "X-User-Id") Integer userId,
            @RequestHeader(name = "X-Is-Admin") Boolean isAdmin) {
        try {
            return switch (info) {
                case "brief" -> ResponseEntity.ok(service.getBotBriefInfo(userId, isAdmin, id));
                case "detail" -> ResponseEntity.ok(service.getBotBriefInfo(userId, isAdmin, id));
                case "edit" -> ResponseEntity.ok(service.getBotBriefInfo(userId, isAdmin, id));
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

    // @PostMapping
    // public ResponseEntity<ResponseDTO> createBot(@RequestBody BotEditInfoDTO dto,
    // @CookieValue("token") String token) {
    // try {
    // return ResponseEntity.ok(service.createBot(dto, token));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Object> updateBot(@PathVariable Integer id,
    // @RequestBody BotEditInfoDTO dto, @CookieValue("token") String token) {
    // try {
    // return ResponseEntity.ok(service.updateBot(id, dto, token));
    // } catch (NoSuchElementException e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @PutMapping("/{id}/likes")
    // public ResponseEntity<Object> likeBot(@PathVariable Integer id,
    // @CookieValue("token") String token){
    // try {
    // return ResponseEntity.ok(service.likeBot(id, token));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @DeleteMapping("/{id}/likes")
    // public ResponseEntity<Object> dislikeBot(@PathVariable Integer id,
    // @CookieValue("token") String token) {
    // try {
    // return ResponseEntity.ok(service.dislikeBot(id, token));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @PutMapping("/{id}/stars")
    // public ResponseEntity<Object> starBot(@PathVariable Integer id,
    // @CookieValue("token") String token){
    // try {
    // return ResponseEntity.ok(service.starBot(id, token));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @DeleteMapping("/{id}/stars")
    // public ResponseEntity<Object> unstarBot(@PathVariable Integer id,
    // @CookieValue("token") String token){
    // try {
    // return ResponseEntity.ok(service.unstarBot(id, token));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @GetMapping("/{botid}/comments")
    // public ResponseEntity<Object> getComments(@PathVariable Integer botid,
    // @RequestParam(defaultValue = "0") Integer page,
    // @RequestParam(defaultValue = "100") Integer pagesize) {
    // try {
    // return ResponseEntity.ok(service.getComments(botid, page, pagesize));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

    // @PostMapping("/{botid}/comments")
    // public ResponseEntity<Object> createComment(@PathVariable Integer botid,
    // @CookieValue("token") String token,
    // @RequestBody CommentRequestDTO request) {
    // try {
    // return ResponseEntity.ok(service.createComment(botid, token,
    // request.getContent()));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ResponseDTO(false, e.getMessage()));
    // }
    // }

}
