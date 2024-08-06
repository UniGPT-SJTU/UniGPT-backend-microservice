package com.unigpt.chat.controllerTest;

import com.unigpt.chat.controller.UserController;
import com.unigpt.chat.service.UserService;
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
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success(){
        ResponseEntity<Object> response = controller.createUser(1, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateUser_Exception(){
        doThrow(new RuntimeException()).when(service).createUser(1, null);
        ResponseEntity<Object> response = controller.createUser(1, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_Success(){
        ResponseEntity<Object> response = controller.updateUserProfile(1, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_AuthenticationException() throws AuthenticationException {
        doThrow(new AuthenticationException()).when(service).updateUserInfo(1, null);
        ResponseEntity<Object> response = controller.updateUserProfile(1, null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateUserProfile_NoSuchElementException() throws AuthenticationException {
        doThrow(new NoSuchElementException()).when(service).updateUserInfo(1, null);
        ResponseEntity<Object> response = controller.updateUserProfile(1, null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
