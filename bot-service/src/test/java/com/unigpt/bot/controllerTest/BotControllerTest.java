package com.unigpt.bot.controllerTest;

import com.unigpt.bot.controller.BotController;
import com.unigpt.bot.dto.BotEditInfoDTO;
import com.unigpt.bot.dto.CommentRequestDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.service.BotService;

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
    void testGetBots_Success(){
        // mock service
        when(service.getBots("", "latest", 0, 20)).thenReturn(null);

        // test controller
        ResponseEntity<Object> response = controller.getBots("", "latest", 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBots_Exception(){
        when(service.getBots("", "latest", 0, 20)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.getBots("", "latest", 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBotProfile_Brief_Success(){
        when(service.getBotBriefInfo(1, true, 1)).thenReturn(null);

        ResponseEntity<Object> response = controller.getBotProfile(1, "brief", 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBotProfile_Detail_Success(){
        when(service.getBotDetailInfo(1, true, 1)).thenReturn(null);

        ResponseEntity<Object> response = controller.getBotProfile(1, "detail", 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBotProfile_Edit_Success(){
        when(service.getBotEditInfo(1, true, 1)).thenReturn(null);

        ResponseEntity<Object> response = controller.getBotProfile(1, "edit", 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetBotProfile_BAD_REQUEST(){
        ResponseEntity<Object> response = controller.getBotProfile(1, "invalid", 1, true);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetBotProfile_Exception(){
        when(service.getBotBriefInfo(1, true, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.getBotProfile(1, "brief", 1, true);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateBot_Success() throws Exception {
        // mock service
        when(service.createBot(1, new BotEditInfoDTO())).thenReturn(new ResponseDTO(true, ""));

        // test controller
        ResponseEntity<ResponseDTO> response = controller.createBot(new BotEditInfoDTO(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateBot_Exception() throws Exception {
        when(service.createBot(1, new BotEditInfoDTO())).thenThrow(new Exception());

        ResponseEntity<ResponseDTO> response = controller.createBot(new BotEditInfoDTO(), 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Success(){
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.updateBot(1, true, 1, dto)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.updateBot(1, dto, 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateBot_NotFound(){
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.updateBot(1, true, 1, dto)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.updateBot(1, dto, 1, true);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBot_Exception(){
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(service.updateBot(1, true, 1, dto)).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = controller.updateBot(1, dto, 1, true);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testLikeBot_Success(){
        when(service.likeBot(1, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.likeBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLikeBot_Exception(){
        when(service.likeBot(1, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.likeBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDislikeBot_Success(){
        when(service.dislikeBot(1, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.dislikeBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDislikeBot_Exception(){
        when(service.dislikeBot(1, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.dislikeBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testStarBot_Success(){
        when(service.starBot(1, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.starBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testStarBot_Exception(){
        when(service.starBot(1, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.starBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUnstarBot_Success(){
        when(service.unstarBot(1, 1)).thenReturn(new ResponseDTO(true, ""));

        ResponseEntity<Object> response = controller.unstarBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUnstarBot_Exception(){
        when(service.unstarBot(1, 1)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.unstarBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetComments_Success(){
        when(service.getComments(1, 0, 20)).thenReturn(null);

        ResponseEntity<Object> response = controller.getComments(1, 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetComments_Exception(){
        when(service.getComments(1, 0, 20)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = controller.getComments(1, 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateComment_Success(){
        when(service.createComment(1, 1, "content")).thenReturn(new ResponseDTO(true, ""));

        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setContent("content");

        ResponseEntity<Object> response = controller.createComment(1, dto, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateComment_Exception(){
        when(service.createComment(1, 1, "content")).thenThrow(new NoSuchElementException());

        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setContent("content");

        ResponseEntity<Object> response = controller.createComment(1, dto, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
