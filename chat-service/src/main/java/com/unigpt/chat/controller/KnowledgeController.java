package com.unigpt.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.service.KnowledgeService;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/internal/knowledge")
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    public KnowledgeController(
            KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping("/upload/{botId}")
    public ResponseEntity<ResponseDTO> uploadFile(
            @PathVariable Integer botId,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(name = "X-User-Id") Integer userId,
            @RequestHeader(name = "X-Is-Admin") Boolean isAdmin) {
        try {
            return ResponseEntity.ok(knowledgeService.uploadFile(userId, isAdmin, botId, file));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}
