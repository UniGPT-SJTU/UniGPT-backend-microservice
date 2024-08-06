package com.unigpt.chat.controllerTest;

import com.unigpt.chat.controller.KnowledgeController;
import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.service.KnowledgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KnowledgeControllerTest {

    @Mock
    private KnowledgeService service;

    @InjectMocks
    private KnowledgeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFile_Success() throws AuthenticationException {
        MultipartFile file = mock(MultipartFile.class);
        when(service.uploadFile(1, true, 1, file)).thenReturn(new ResponseDTO(true, ""));
        ResponseEntity<ResponseDTO> response = controller.uploadFile(1, file, 1, true);
         assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUploadFile_NoSuchElementException() throws AuthenticationException {
        MultipartFile file = mock(MultipartFile.class);
        when(service.uploadFile(1, true, 1, file)).thenThrow(new AuthenticationException());
        ResponseEntity<ResponseDTO> response = controller.uploadFile(1, file, 1, true);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUploadFile_AuthenticationException() throws AuthenticationException {
        MultipartFile file = mock(MultipartFile.class);
        when(service.uploadFile(1, true, 1, file)).thenThrow(new AuthenticationException());
        ResponseEntity<ResponseDTO> response = controller.uploadFile(1, file, 1, true);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUploadFile_IllegalArgumentException() throws AuthenticationException {
        MultipartFile file = mock(MultipartFile.class);
        when(service.uploadFile(1, true, 1, file)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ResponseDTO> response = controller.uploadFile(1, file, 1, true);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
