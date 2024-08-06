package com.unigpt.chat.controllerTest;

import com.unigpt.chat.controller.BotController;
import com.unigpt.chat.dto.BotEditInfoDTO;
import com.unigpt.chat.dto.CreateBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.service.BotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


public class BotControllerTest {

    @Mock
    private BotService service;

    @InjectMocks
    private BotController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBotHistory_Success() throws Exception {
        // mock service
        when(service.getBotHistory(1, 1, 1, 1)).thenReturn(new GetBotHistoryOkResponseDTO(0, new ArrayList<>()));

        // test controller
        ResponseEntity<Object> response = controller.getBotHistory(1, 1, 1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBotHistory_Exception() {
        when(service.getBotHistory(1, 1, 1, 1)).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = controller.getBotHistory(1, 1, 1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateBotHistory_Success() throws Exception {
        // mock service
        when(service.createBotHistory(1, 1, new ArrayList<>())).thenReturn(new CreateBotHistoryOkResponseDTO(false, "", 0, ""));

        // test controller
        ResponseEntity<Object> response = controller.createBotHistory(1, new ArrayList<>(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateBotHistory_NoSuchElementException() throws Exception {
        when(service.createBotHistory(1, 1, new ArrayList<>())).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.createBotHistory(1, new ArrayList<>(), 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateBotHistory_Exception() throws Exception {
        when(service.createBotHistory(1, 1, new ArrayList<>())).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = controller.createBotHistory(1, new ArrayList<>(), 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateBot_Success(){
        // test controller
        ResponseEntity<ResponseDTO> response = controller.createBot(1, new BotEditInfoDTO(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateBot_Exception() throws Exception {
        doThrow(new RuntimeException()).when(service).createBot(1, 1, new BotEditInfoDTO());

        ResponseEntity<ResponseDTO> response = controller.createBot(1, new BotEditInfoDTO(), 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Success(){
        // test controller
        ResponseEntity<Object> response = controller.updateBot(1, new BotEditInfoDTO(), 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateBot_NoSuchElementException() throws Exception {
        doThrow(new NoSuchElementException()).when(service).updateBot(1, true, 1, new BotEditInfoDTO());

        ResponseEntity<Object> response = controller.updateBot(1, new BotEditInfoDTO(), 1, true);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBot_AuthenticationException() throws Exception {
        doThrow(new AuthenticationException()).when(service).updateBot(1, true, 1, new BotEditInfoDTO());

        ResponseEntity<Object> response = controller.updateBot(1, new BotEditInfoDTO(), 1, true);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Exception() throws Exception {
        doThrow(new RuntimeException()).when(service).updateBot(1, true, 1, new BotEditInfoDTO());

        ResponseEntity<Object> response = controller.updateBot(1, new BotEditInfoDTO(), 1, true);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
