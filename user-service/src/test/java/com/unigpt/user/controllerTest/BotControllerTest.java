package com.unigpt.user.controllerTest;

import com.unigpt.user.controller.BotController;
import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.service.BotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BotControllerTest {

    @Mock
    private BotService service;

    @InjectMocks
    private BotController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBot_Success() throws Exception {
        // mock service
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.createBot(dto, 1,  1)).thenReturn(new ResponseDTO(true, ""));

        // test controller
        ResponseEntity<ResponseDTO> response = controller.createBot(dto, 1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateBot_Exception() throws Exception {
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.createBot(dto, 1, 1)).thenThrow(new Exception());

        ResponseEntity<ResponseDTO> response = controller.createBot(dto, 1, 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Success(){
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.updateBot(1, dto, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.updateBot(1, dto, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Exception(){
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.updateBot(1, dto, 1)).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = controller.updateBot(1, dto, 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
