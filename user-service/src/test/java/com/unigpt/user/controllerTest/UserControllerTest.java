package com.unigpt.user.controllerTest;

import com.unigpt.user.controller.UserController;
import com.unigpt.user.dto.UserUpdateDTO;
import com.unigpt.user.model.User;
import com.unigpt.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // mock service
        when(service.createUser("", "", "")).thenReturn(1);

        // test controller
        ResponseEntity<Object> response = controller.createUser("", "", "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateUser_Exception() {
        when(service.createUser("", "", "")).thenThrow(new RuntimeException(""));

        ResponseEntity<Object> response = controller.createUser("", "", "");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetUserProfile_Success() {
        // mock service
        when(service.findUserById(1)).thenReturn(new User());

        // test controller
        ResponseEntity<Object> response = controller.getUserProfile(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUserProfile_Exception() {
        when(service.findUserById(1)).thenThrow(new RuntimeException(""));

        ResponseEntity<Object> response = controller.getUserProfile(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_Success() {
        UserUpdateDTO dto = new UserUpdateDTO();
        ResponseEntity<Object> response = controller.updateUserProfile(1, dto, 1, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_PermissionDenied() {
        UserUpdateDTO dto = new UserUpdateDTO();
        ResponseEntity<Object> response = controller.updateUserProfile(1, dto, 2, false);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_Exception() {
        UserUpdateDTO dto = new UserUpdateDTO();
        doThrow(new RuntimeException()).when(service).updateUserInfo(1, dto);
        ResponseEntity<Object> response = controller.updateUserProfile(1, dto, 1, true);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsers_Success() {
        ResponseEntity<Object> response = controller.getUsers(0, 20, "keyword", "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUsers_Exception() {
        doThrow(new RuntimeException()).when(service).getUsers(0, 20, "keyword", "");
        ResponseEntity<Object> response = controller.getUsers(0, 20, "keyword", "");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUsedBots_Success() {
        ResponseEntity<Object> response = controller.getUsedBots(1, 0, 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUsedBots_NoSuchElementException() {
        doThrow(new NoSuchElementException()).when(service).getUsedBots(1, 0, 20);
        ResponseEntity<Object> response = controller.getUsedBots(1, 0, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUseBot_Success() {
        ResponseEntity<Object> response = controller.useBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUseBot_Exception() {
        doThrow(new NoSuchElementException()).when(service).useBot(1, 1);
        ResponseEntity<Object> response = controller.useBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDisuseBot_Success() {
        ResponseEntity<Object> response = controller.disuseBot(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDisuseBot_Exception() {
        doThrow(new NoSuchElementException()).when(service).disuseBot(1, 1);
        ResponseEntity<Object> response = controller.disuseBot(1, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
