package com.unigpt.bot.serviceTest;

import com.unigpt.bot.LLMArgs.LLMArgs;
import com.unigpt.bot.dto.*;
import com.unigpt.bot.model.Bot;
import com.unigpt.bot.model.ChatType;
import com.unigpt.bot.model.Plugin;
import com.unigpt.bot.model.User;
import com.unigpt.bot.repository.BotRepository;
import com.unigpt.bot.repository.UserRepository;
import com.unigpt.bot.repository.PromptChatRepository;
import com.unigpt.bot.repository.PluginRepository;
import com.unigpt.bot.repository.CommentRepository;
import com.unigpt.bot.client.UserServiceClient;
import com.unigpt.bot.client.ChatServiceClient;
import com.unigpt.bot.client.PluginServiceClient;

import com.unigpt.bot.service.BotService;
import com.unigpt.bot.serviceimpl.BotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BotServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBots_latest_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.countByIsPublished()).thenReturn(1);
        when(botRepository.countByNameContainsAndIsPublished("q")).thenReturn(1);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Bot> page = new PageImpl<>(List.of(new Bot()));
        when(botRepository.findBotsByIsPublishedOrderByIdDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByIdDesc("q", pageable)).thenReturn(page);
        when(botRepository.findBotsByIsPublishedOrderByLikeNumberDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByLikeNumberDesc("q", pageable)).thenReturn(page);

        GetBotsOkResponseDTO getBotsOkResponseDTO = botService.getBots("q", "latest", 0, 20);
        assertEquals(1, getBotsOkResponseDTO.getTotal());
    }

    @Test
    void testGetBots_like_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.countByIsPublished()).thenReturn(1);
        when(botRepository.countByNameContainsAndIsPublished("q")).thenReturn(1);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Bot> page = new PageImpl<>(List.of(new Bot()));
        when(botRepository.findBotsByIsPublishedOrderByIdDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByIdDesc("q", pageable)).thenReturn(page);
        when(botRepository.findBotsByIsPublishedOrderByLikeNumberDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByLikeNumberDesc("q", pageable)).thenReturn(page);

        GetBotsOkResponseDTO getBotsOkResponseDTO = botService.getBots("q", "like", 0, 20);
        assertEquals(1, getBotsOkResponseDTO.getTotal());
    }

    @Test
    void testGetBots_Exception(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.countByIsPublished()).thenReturn(1);
        when(botRepository.countByNameContainsAndIsPublished("q")).thenReturn(1);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Bot> page = new PageImpl<>(List.of(new Bot()));
        when(botRepository.findBotsByIsPublishedOrderByIdDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByIdDesc("q", pageable)).thenReturn(page);
        when(botRepository.findBotsByIsPublishedOrderByLikeNumberDesc(pageable)).thenReturn(page);
        when(botRepository.findBotsByNameContainsAndIsPublishedOrderByLikeNumberDesc("q", pageable)).thenReturn(page);

        try {
            GetBotsOkResponseDTO getBotsOkResponseDTO = botService.getBots("q", "invalid", 0, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotBriefInfo_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(true);

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        BotBriefInfoDTO botBriefInfoDTO = botService.getBotBriefInfo(1, true, 1);
        assertNotNull(botBriefInfoDTO);
    }

    @Test
    void testGetBotBriefInfo_BotNotFound_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            BotBriefInfoDTO botBriefInfoDTO = botService.getBotBriefInfo(1, true, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotBriefInfo_BotNotPublished_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(false);
        when(botRepository.findById(1)).thenReturn(Optional.of(bot));

        try {
            BotBriefInfoDTO botBriefInfoDTO = botService.getBotBriefInfo(2, false, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotDetailInfo_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(true);
        bot.setLikeUsers(List.of(user));
        bot.setStarUsers(List.of(user));
        bot.setPlugins(List.of());

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        BotDetailInfoDTO botDetailInfoDTO = botService.getBotDetailInfo(1, true, 1);
        assertNotNull(botDetailInfoDTO);
    }

    @Test
    void testGetBotDetailInfo_BotNotFound_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            BotDetailInfoDTO botDetailInfoDTO = botService.getBotDetailInfo(1, true, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotDetailInfo_UserNotFound_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(true);
        bot.setLikeUsers(List.of(user));
        bot.setStarUsers(List.of(user));
        bot.setPlugins(List.of());

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        try {
            BotDetailInfoDTO botDetailInfoDTO = botService.getBotDetailInfo(1, true, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotDetailInfo_BotNotPublished_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(false);
        when(botRepository.findById(1)).thenReturn(Optional.of(bot));

        try {
            BotDetailInfoDTO botDetailInfoDTO = botService.getBotDetailInfo(1, true, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
    
    @Test
    void testGetBotEditInfo_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(true);
        bot.setLikeUsers(List.of(user));
        bot.setStarUsers(List.of(user));
        bot.setPlugins(List.of());
        bot.setIsPrompted(true);
        bot.setPromptChats(List.of());
        bot.setPromptKeys(List.of());

        when(botRepository.findById(1)).thenReturn(Optional.of(bot));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        BotEditInfoDTO botEditInfoDTO = botService.getBotEditInfo(1, true, 1);
        assertNotNull(botEditInfoDTO);
    }

    @Test
    void testGetBotEditInfo_BotNotFound_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.findById(1)).thenReturn(Optional.empty());

        try {
            BotEditInfoDTO botEditInfoDTO = botService.getBotEditInfo(1, true, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetBotEditInfo_BotNotPublished_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setId(1);
        Bot bot = new Bot();
        bot.setId(1);
        bot.setCreator(user);
        bot.setIsPublished(true);
        bot.setLikeUsers(List.of(user));
        bot.setStarUsers(List.of(user));
        bot.setPlugins(List.of());
        bot.setIsPrompted(true);
        bot.setPromptChats(List.of());
        bot.setPromptKeys(List.of());
        when(botRepository.findById(1)).thenReturn(Optional.of(bot));

        try {
            BotEditInfoDTO botEditInfoDTO = botService.getBotEditInfo(2, false, 1);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testCreateBot_Success(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        PromptChatDTO promptChatDTO = new PromptChatDTO();
        promptChatDTO.setContent("content");
        promptChatDTO.setType(ChatType.USER);
        dto.setPromptChats(List.of(promptChatDTO));
        PluginBriefInfoDTO pluginBriefInfoDTO = new PluginBriefInfoDTO();
        pluginBriefInfoDTO.setId(1);
        dto.setPlugins(List.of(pluginBriefInfoDTO));
        User user = new User();
        user.setCreateBots(List.of());

        when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        when(botRepository.existsById(botid)).thenReturn(false);
        when(pluginRepository.findById(1)).thenReturn(Optional.of(new Plugin()));


        try {
            ResponseDTO responseDTO = botService.createBot(1, dto);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testCreateBot_UserNotFound_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        PromptChatDTO promptChatDTO = new PromptChatDTO();
        promptChatDTO.setContent("content");
        promptChatDTO.setType(ChatType.USER);
        dto.setPromptChats(List.of(promptChatDTO));
        PluginBriefInfoDTO pluginBriefInfoDTO = new PluginBriefInfoDTO();
        pluginBriefInfoDTO.setId(1);
        dto.setPlugins(List.of(pluginBriefInfoDTO));
        User user = new User();
        user.setCreateBots(List.of());

        when(userRepository.findById(userid)).thenReturn(Optional.empty());

        try {
            ResponseDTO responseDTO = botService.createBot(1, dto);
            fail();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testCreateBot_EmptyPromptChats(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        dto.setPromptChats(List.of());
        PluginBriefInfoDTO pluginBriefInfoDTO = new PluginBriefInfoDTO();
        pluginBriefInfoDTO.setId(1);
        dto.setPlugins(List.of(pluginBriefInfoDTO));
        User user = new User();
        user.setCreateBots(List.of());

        when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        when(botRepository.existsById(botid)).thenReturn(false);
        when(pluginRepository.findById(1)).thenReturn(Optional.of(new Plugin()));


        try {
            ResponseDTO responseDTO = botService.createBot(1, dto);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Prompt chats should not be empty"));
        }
    }

    @Test
    void testCreateBot_UserType(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PromptChatRepository promptChatRepository = Mockito.mock(PromptChatRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        UserServiceClient userServiceClient = Mockito.mock(UserServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);
        BotService botService = new BotServiceImpl(botRepository, userRepository, promptChatRepository, pluginRepository, commentRepository, userServiceClient, chatServiceClient, pluginServiceClient);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        PromptChatDTO promptChatDTO = new PromptChatDTO();
        promptChatDTO.setContent("content");
        promptChatDTO.setType(ChatType.BOT);
        dto.setPromptChats(List.of(promptChatDTO));
        PluginBriefInfoDTO pluginBriefInfoDTO = new PluginBriefInfoDTO();
        pluginBriefInfoDTO.setId(1);
        dto.setPlugins(List.of(pluginBriefInfoDTO));
        User user = new User();
        user.setCreateBots(List.of());


        when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        when(botRepository.existsById(botid)).thenReturn(false);
        when(pluginRepository.findById(1)).thenReturn(Optional.of(new Plugin()));

        try {
            ResponseDTO responseDTO = botService.createBot(1, dto);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Last prompt chat should be user type");
        }
    }

}
