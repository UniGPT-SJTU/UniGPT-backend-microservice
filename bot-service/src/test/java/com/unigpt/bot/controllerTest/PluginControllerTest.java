package com.unigpt.bot.controllerTest;

import com.unigpt.bot.controller.PluginController;
import com.unigpt.bot.dto.PluginEditInfoDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.service.PluginService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PluginControllerTest {

    @Mock
    private PluginService service;

    @InjectMocks
    private PluginController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlugin_Success() {
        // mock service
        PluginEditInfoDTO dto = new PluginEditInfoDTO("", "");
        when(service.createPlugin(1, dto)).thenReturn(new ResponseDTO(true, ""));

        // test controller
        ResponseEntity<Object> response = controller.createPlugin(1, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreatePlugin_Exception() {
        PluginEditInfoDTO dto = new PluginEditInfoDTO("", "");
        when(service.createPlugin(1, dto)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.createPlugin(1, dto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
