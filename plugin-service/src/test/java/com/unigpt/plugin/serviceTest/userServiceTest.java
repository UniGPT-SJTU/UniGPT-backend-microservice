package com.unigpt.plugin.serviceTest;

import com.unigpt.plugin.Repository.UserRepository;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.model.User;
import com.unigpt.plugin.service.UserService;
import com.unigpt.plugin.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class userServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);

        Integer userid = 1;
        String name = "Test";
        when(userRepository.findByTrueId(userid)).thenReturn(Optional.empty());
        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        ResponseDTO responseDTO = userServiceImpl.createUser(userid, name);
        assertTrue(responseDTO.getOk());
    }

    @Test
    void testCreateUser_Failure() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);

        Integer userid = 1;
        String name = "Test";
        when(userRepository.findByTrueId(userid)).thenReturn(Optional.of(new User()));
        when(userRepository.findByName(name)).thenReturn(Optional.of(new User()));

        ResponseDTO responseDTO = userServiceImpl.createUser(userid, name);
        assertFalse(responseDTO.getOk());
    }
}
