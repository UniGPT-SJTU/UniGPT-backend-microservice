package com.unigpt.plugin.controllerTest;

import com.unigpt.plugin.controller.PluginController;
import com.unigpt.plugin.dto.PluginInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.dtoFactory.dtoFactory;
import com.unigpt.plugin.service.PluginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class pluginControllerTest {

    @Mock
    private PluginService pluginService;

    @InjectMocks
    private PluginController pluginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlugin_Success() throws Exception {
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(0);
        when(pluginService.createPlugin(dto, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = pluginController.createPlugin(dto, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreatePlugin_Exception() throws Exception {
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(0);
        when(pluginService.createPlugin(dto, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = pluginController.createPlugin(dto, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetPlugins_Success(){
        when(pluginService.getPlugins("", "latest", 0, 20))
                .thenReturn(dtoFactory.createGetPluginsOkResponseDTO(20));

        ResponseEntity<Object> response = pluginController.getPlugins("", "latest", 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetPlugins_Exception(){
        when(pluginService.getPlugins("", "latest", 0, 20))
                .thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = pluginController.getPlugins("", "latest", 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetPluginInfo_Success(){
        when(pluginService.getPluginInfo(1, 1))
                .thenReturn(dtoFactory.createPluginDetailInfoDTO(1));

        ResponseEntity<Object> response = pluginController.getPluginInfo(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetPluginInfo_Exception(){
        when(pluginService.getPluginInfo(1, 1))
                .thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = pluginController.getPluginInfo(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
