package com.unigpt.chat.serviceTest;

import com.unigpt.chat.client.BotServiceClient;
import com.unigpt.chat.client.UserServiceClient;
import com.unigpt.chat.dto.*;
import com.unigpt.chat.model.*;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.repository.ChatRepository;
import com.unigpt.chat.repository.UserRepository;
import com.unigpt.chat.serviceimpl.ChatHistoryServiceImpl;
import com.unigpt.chat.serviceimpl.UserServiceImpl;
import dev.langchain4j.data.message.ChatMessageType;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.naming.AuthenticationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // mock repository
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User(1, "name", "avatar"));

        // test service
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UpdateUserInfoRequestDTO dto = new UpdateUserInfoRequestDTO();
        userService.createUser(1, dto);
    }

    @Test
    void testCreateUser_Exception() {
        // mock repository
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        // test service
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UpdateUserInfoRequestDTO dto = new UpdateUserInfoRequestDTO();
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(1, dto));
    }

    @Test
    void testUpdateUserInfo_Success() throws AuthenticationException {
        // mock repository
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(new User(1, "name", "avatar")));

        // test service
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UpdateUserInfoRequestDTO dto = new UpdateUserInfoRequestDTO();
        userService.updateUserInfo(1, dto);
    }

    @Test
    void testUpdateUserInfo_Exception() {
        // mock repository
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // test service
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UpdateUserInfoRequestDTO dto = new UpdateUserInfoRequestDTO();
        assertThrows(NoSuchElementException.class, () -> userService.updateUserInfo(1, dto));
    }
}
