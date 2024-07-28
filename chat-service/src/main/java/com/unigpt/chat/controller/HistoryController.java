package com.unigpt.chat.controller;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.service.ChatHistoryService;

@RestController
@RequestMapping("/internal/histories")
public class HistoryController {

    private final ChatHistoryService service;

    public HistoryController(ChatHistoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}/chats")
    public ResponseEntity<Object> getChats(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pagesize,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.getChats(userId, id, page, pagesize));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{historyid}/promptlist")
    public ResponseEntity<Object> getPromptList(
            @PathVariable Integer historyid,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            return ResponseEntity.ok(service.getPromptList(userId, historyid));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{historyid}")
    public ResponseEntity<Object> deleteHistory(
            @PathVariable Integer historyid,
            @RequestHeader(name = "X-User-Id") Integer userId) {
        try {
            service.deleteHistory(userId, historyid);
            return ResponseEntity.ok().body(new ResponseDTO(true, "History deleted"));
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
