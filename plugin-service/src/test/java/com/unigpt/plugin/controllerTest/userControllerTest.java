package com.unigpt.plugin.controllerTest;

import com.unigpt.plugin.controller.UserController;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class userControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        Integer userid = 1;
        String name = "test-name";

        when(userService.createUser(userid, name)).thenReturn(new ResponseDTO(true, "User created"));
        ResponseEntity<ResponseDTO> response = userController.createUser(userid, name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
