package com.unigpt.chat.controllerTest;

import com.unigpt.chat.controller.HistoryController;
import com.unigpt.chat.dto.GetChatsOkResponseDTO;
import com.unigpt.chat.service.ChatHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


public class HistoryControllerTest {

    @Mock
    private ChatHistoryService service;

    @InjectMocks
    private HistoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChats_Success() throws AuthenticationException {
        when(service.getChats(1, 1, 1, 1)).thenReturn(new GetChatsOkResponseDTO(0, new ArrayList<>()));
        ResponseEntity<Object> response = controller.getChats(1, 1, 1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetChats_NoSuchElementException() throws AuthenticationException {
        when(service.getChats(1, 1, 1, 1)).thenThrow(new AuthenticationException());
        ResponseEntity<Object> response = controller.getChats(1, 1, 1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetChats_AuthenticationException() throws AuthenticationException {
        when(service.getChats(1, 1, 1, 1)).thenThrow(new AuthenticationException());
        ResponseEntity<Object> response = controller.getChats(1, 1, 1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetPromptList_Success() throws AuthenticationException {
        when(service.getPromptList(1, 1)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = controller.getPromptList(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetPromptList_NoSuchElementException() throws AuthenticationException {
        when(service.getPromptList(1, 1)).thenThrow(new AuthenticationException());
        ResponseEntity<Object> response = controller.getPromptList(1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetPromptList_AuthenticationException() throws AuthenticationException {
        when(service.getPromptList(1, 1)).thenThrow(new AuthenticationException());
        ResponseEntity<Object> response = controller.getPromptList(1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteHistory_Success(){
        ResponseEntity<Object> response = controller.deleteHistory(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteHistory_NoSuchElementException() throws Exception {
        doThrow(new AuthenticationException()).when(service).deleteHistory(1, 1);
        ResponseEntity<Object> response = controller.deleteHistory(1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteHistory_AuthenticationException() throws Exception {
        doThrow(new AuthenticationException()).when(service).deleteHistory(1, 1);
        ResponseEntity<Object> response = controller.deleteHistory(1, 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
