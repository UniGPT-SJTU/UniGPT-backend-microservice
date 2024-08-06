package com.unigpt.chat.serviceTest;

import com.unigpt.chat.client.BotServiceClient;
import com.unigpt.chat.client.UserServiceClient;
import com.unigpt.chat.dto.*;
import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.ChatType;
import com.unigpt.chat.model.History;
import com.unigpt.chat.model.User;
import com.unigpt.chat.repository.BotRepository;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.repository.UserRepository;
import com.unigpt.chat.serviceimpl.BotServiceImpl;
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
import static org.mockito.Mockito.when;

public class BotServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBotHistory_Success() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(historyRepository.findHistoriesByBotAndUserId(PageRequest.of(0, 10), bot, 1)).thenReturn(Page.empty());
        when(historyRepository.countByBotAndUserId(bot, 1)).thenReturn(0);

        GetBotHistoryOkResponseDTO response = botService.getBotHistory(1, 1, 0, 10);
        assert response.getHistories().isEmpty();
    }

    @Test
    void testGetBotHistory_Exception() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            GetBotHistoryOkResponseDTO response = botService.getBotHistory(1, 1, 0, 10);
        } catch (Exception e) {
            assert e instanceof NoSuchElementException;
        }
    }

    @Test
    void testCreateBotHistory_Success() throws Exception {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));
        List<PromptDTO> promptList = List.of(new PromptDTO("key", "value"));
        BotHistoryInfoDTO dto = new BotHistoryInfoDTO();
        dto.setPromptKeys(List.of("key"));
        dto.setPromptChats(List.of(new PromptChatDTO(ChatType.USER, "content")));

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botServiceClient.getBotHistoryInfo(1)).thenReturn(dto);
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        when(historyRepository.save(Mockito.any())).thenReturn(history);

        CreateBotHistoryOkResponseDTO response = botService.createBotHistory(1, 1, promptList);
        assertTrue(response.getOk());
    }

    @Test
    void testCreateBotHistory_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));
        List<PromptDTO> promptList = List.of(new PromptDTO("key", "value"));
        BotHistoryInfoDTO dto = new BotHistoryInfoDTO();
        dto.setPromptKeys(List.of("key"));
        dto.setPromptChats(List.of(new PromptChatDTO(ChatType.USER, "content")));

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            CreateBotHistoryOkResponseDTO response = botService.createBotHistory(1, 1, promptList);
        } catch (Exception e) {
            assert e instanceof NoSuchElementException;
        }
    }

    @Test
    void testCreateBotHistory_BadRequestException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));
        List<PromptDTO> promptList = List.of(new PromptDTO("key", "value"));
        BotHistoryInfoDTO dto = new BotHistoryInfoDTO();
        dto.setPromptKeys(List.of("key"));
        dto.setPromptChats(List.of(new PromptChatDTO(ChatType.USER, "content")));

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botServiceClient.getBotHistoryInfo(1)).thenReturn(dto);
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        try {
            CreateBotHistoryOkResponseDTO response = botService.createBotHistory(1, 1, List.of(new PromptDTO("key", "value1")));
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }

    @Test
    void testCreateBotHistory_AuthenticationException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));
        List<PromptDTO> promptList = List.of(new PromptDTO("key", "value"));
        BotHistoryInfoDTO dto = new BotHistoryInfoDTO();
        dto.setPromptKeys(List.of("key"));
        dto.setPromptChats(List.of(new PromptChatDTO(ChatType.USER, "content")));

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botServiceClient.getBotHistoryInfo(1)).thenReturn(dto);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        try {
            CreateBotHistoryOkResponseDTO response = botService.createBotHistory(1, 1, promptList);
        } catch (Exception e) {
            assert e instanceof AuthenticationException;
        }
    }

    @Test
    void testCreateBotHistory_RuntimeException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        History history = new History();
        history.setLastActiveTime(Date.from(new Date().toInstant()));
        List<PromptDTO> promptList = List.of(new PromptDTO("key", "value"));
        BotHistoryInfoDTO dto = new BotHistoryInfoDTO();
        dto.setPromptKeys(List.of("key"));
        dto.setPromptChats(List.of(new PromptChatDTO(ChatType.USER, "content")));

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botServiceClient.getBotHistoryInfo(1)).thenReturn(dto);
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        when(historyRepository.save(Mockito.any())).thenThrow(new RuntimeException());

        try {
            CreateBotHistoryOkResponseDTO response = botService.createBotHistory(1, 1, promptList);
        } catch (Exception e) {
            assert e instanceof RuntimeException;
        }
    }

    @Test
    void testCreateBot_Success() throws Exception {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        botService.createBot(1, 1, new BotEditInfoDTO());
    }

    @Test
    void testCreateBot_AuthenticationException() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        try {
            botService.createBot(1, 1, new BotEditInfoDTO());
            fail();
        } catch (Exception e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void testCreateBot_RuntimeException() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(botRepository.findById(1)).thenReturn(Optional.of(new Bot()));

        try {
            botService.createBot(1, 1, new BotEditInfoDTO());
            fail();
        } catch (Exception e) {
            assertEquals("Bot already exists", e.getMessage());
        }
    }

    @Test
    void testUpdateBot_Success() throws Exception {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        User user = new User();
        user.setId(1);
        bot.setCreator(user);

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botRepository.save(Mockito.any())).thenReturn(bot);

        botService.updateBot(1, false, 1, new BotEditInfoDTO());
    }

    @Test
    void testUpdateBot_Success2() throws Exception {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        User user = new User();
        user.setId(2);
        bot.setCreator(user);

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botRepository.save(Mockito.any())).thenReturn(bot);

        botService.updateBot(1, true, 1, new BotEditInfoDTO());
    }

    @Test
    void testUpdateBot_IllegalArgumentException() throws Exception {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        Bot bot = new Bot();
        bot.setId(1);
        User user = new User();
        user.setId(2);
        bot.setCreator(user);

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(botRepository.save(Mockito.any())).thenReturn(bot);

        try {
            botService.updateBot(1, false, 1, new BotEditInfoDTO());
            fail();
        }
        catch (Exception e) {
            assertEquals("User not authorized to update bot", e.getMessage());
        }
    }

    @Test
    void testUpdateBot_NoSuchElementException() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        MemoryRepository memoryRepository = Mockito.mock(MemoryRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        BotServiceImpl botService = new BotServiceImpl(botRepository, historyRepository, memoryRepository, userRepository, botServiceClient, userServiceClient);

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            botService.updateBot(1, false, 1, new BotEditInfoDTO());
            fail();
        }
        catch (Exception e) {
            assertEquals("Bot not found for ID: 1", e.getMessage());
        }
    }
}
