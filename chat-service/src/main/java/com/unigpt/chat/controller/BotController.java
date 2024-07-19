package com.unigpt.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // @PostMapping("/{id}/histories")
    // public ResponseEntity<Object> createBotHistory(@PathVariable Integer id, @CookieValue("token") String token, @RequestBody List<PromptDTO> promptList) {
    //     try {
    //         return ResponseEntity.ok(service.createBotHistory(id, token, promptList));
    //     } catch (NoSuchElementException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(new ResponseDTO(false, e.getMessage()));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(new ResponseDTO(false, e.getMessage()));
    //     }
    // }
    // @PostMapping
    // public ResponseEntity<ResponseDTO> createBot(@RequestBody BotEditInfoDTO dto, @CookieValue("token") String token) {
    //     try {
    //         return ResponseEntity.ok(service.createBot(dto, token));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(new ResponseDTO(false, e.getMessage()));
    //     }
    // }
    // @PutMapping("/{id}")
    // public ResponseEntity<Object> updateBot(@PathVariable Integer id, @RequestBody BotEditInfoDTO dto, @CookieValue("token") String token) {
    //     try {
    //         return ResponseEntity.ok(service.updateBot(id, dto, token));
    //     } catch (NoSuchElementException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(new ResponseDTO(false, e.getMessage()));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(new ResponseDTO(false, e.getMessage()));
    //     }
    // }
}
