package com.unigpt.bot.controllerTest;


import com.unigpt.bot.controller.PluginController;
import com.unigpt.bot.controller.UserController;
import com.unigpt.bot.dto.PluginEditInfoDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.dto.UpdateUserInfoRequestDTO;
import com.unigpt.bot.service.PluginService;

import com.unigpt.bot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Mock
    UserService service;

    @InjectMocks
    UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // mock service
        // test controller
        ResponseEntity<Object> response = controller.createUser(1, new UpdateUserInfoRequestDTO());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateUser_Exception() {
        doThrow(new NoSuchElementException()).when(service).createUser(1, new UpdateUserInfoRequestDTO());

        ResponseEntity<Object> response = controller.createUser(1, new UpdateUserInfoRequestDTO());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_Success() {
        // mock service
        // test controller
        ResponseEntity<Object> response = controller.updateUserProfile(1, new UpdateUserInfoRequestDTO(), 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_AuthenticationException() throws AuthenticationException {
        doThrow(new AuthenticationException()).when(service).updateUserInfo(1, 1, new UpdateUserInfoRequestDTO());

        ResponseEntity<Object> response = controller.updateUserProfile(1, new UpdateUserInfoRequestDTO(), 1);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_Exception() throws AuthenticationException {
        doThrow(new NoSuchElementException()).when(service).updateUserInfo(1, 1, new UpdateUserInfoRequestDTO());

        ResponseEntity<Object> response = controller.updateUserProfile(1, new UpdateUserInfoRequestDTO(), 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetStarredBots_Success() {
        // mock service
        // test controller
        ResponseEntity<Object> response = controller.getStarredBots(1, 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetStarredBots_Exception() {
        doThrow(new NoSuchElementException()).when(service).getStarredBots(1, 0, 20);

        ResponseEntity<Object> response = controller.getStarredBots(1, 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCreatedBots_Success() {
        // mock service
        // test controller
        ResponseEntity<Object> response = controller.getCreatedBots(1, 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCreatedBots_Exception() {
        doThrow(new NoSuchElementException()).when(service).getCreatedBots(1, 0, 20);

        ResponseEntity<Object> response = controller.getCreatedBots(1, 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
