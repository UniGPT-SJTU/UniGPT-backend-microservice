package com.unigpt.plugin.controllerTest;

import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.factory.dtoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.unigpt.plugin.controller.BotController;
import com.unigpt.plugin.service.BotService;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class botControllerTest {

    @Mock
    private BotService service;

    @InjectMocks
    private BotController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBot_Success(){
        // mock service
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(0);
        when(service.createBot(dto, 1)).thenReturn(new ResponseDTO(true, ""));

        // test controller
        ResponseEntity<ResponseDTO> response = controller.createBot(dto, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateBot_Exception() {
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(0);
        when(service.createBot(dto, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<ResponseDTO> response = controller.createBot(dto, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Success() {
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(0);
        when(service.updateBot(dto, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<ResponseDTO> response = controller.updateBot(dto, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateBot_NoSuchElementException() {
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(0);
        when(service.updateBot(dto, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<ResponseDTO> response = controller.updateBot(dto, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Exception() {
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(0);
        when(service.updateBot(dto, 1)).thenThrow(new ArithmeticException());

        ResponseEntity<ResponseDTO> response = controller.updateBot(dto, 1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
