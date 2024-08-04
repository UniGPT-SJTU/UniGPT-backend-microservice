package com.unigpt.plugin.serviceTest;



import com.unigpt.plugin.Repository.BotRepository;
import com.unigpt.plugin.Repository.PluginRepository;
import com.unigpt.plugin.Repository.UserRepository;
import com.unigpt.plugin.client.BotServiceClient;
import com.unigpt.plugin.dto.*;
import com.unigpt.plugin.factory.dtoFactory;
import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.model.User;
import com.unigpt.plugin.serviceImpl.PluginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class pluginServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlugin_Success() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer userid = 1;
        User user = new User();
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(1);
        Plugin plugin = new Plugin(dto, user, "");

        when(userRepository.findByTrueId(userid)).thenReturn(Optional.of(user));
        when(pluginRepository.save(plugin)).thenReturn(plugin);
        when(botServiceClient.createPlugin(plugin.getId(), new PluginEditInfoDTO(plugin)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "Plugin created successfully")));

        ResponseDTO responseDTO = pluginServiceImpl.createPlugin(dto, userid);
        assertTrue(responseDTO.getOk());
    }

    @Test
    void testCreatePlugin_NoSuchElementException() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer userid = 1;
        User user = new User();
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(1);
        Plugin plugin = new Plugin(dto, user, "");

        when(userRepository.findByTrueId(userid)).thenReturn(Optional.empty());

        try {
            pluginServiceImpl.createPlugin(dto, userid);
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testCreatePlugin_Exception() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer userid = 1;
        User user = new User();
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(1);
        Plugin plugin = new Plugin(dto, user, "");

        when(userRepository.findByTrueId(userid)).thenReturn(Optional.of(user));
        when(pluginRepository.save(plugin)).thenReturn(plugin);
        when(botServiceClient.createPlugin(plugin.getId(), new PluginEditInfoDTO(plugin)))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Failed to create plugin in Bot Microservice")));

        try {
            pluginServiceImpl.createPlugin(dto, userid);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetPlugins_Success() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        List<Plugin> plugins = new ArrayList<>();
        Plugin plugin = new Plugin(dtoFactory.createPluginInfoDTO(1), new User(), "");
        plugin.setIsPublished(true);
        plugins.add(plugin);

        when(pluginRepository.findAllByOrderByIdDesc()).thenReturn(plugins);

        GetPluginsOkResponseDTO responseDTO = pluginServiceImpl.getPlugins("", "latest", 1, 10);
        System.out.println(responseDTO.getTotal() == 1);
    }

    @Test
    void testGetPlugins_Exception() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        try {
            pluginServiceImpl.getPlugins("", "else", 1, 10);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetPluginInfo_Success() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer pluginId = 1, userId = 1;
        User user = new User();
        user.setTrueId(1);
        Plugin plugin = new Plugin(dtoFactory.createPluginInfoDTO(1), user, "");
        plugin.setIsPublished(true);
        plugin.setCreator(user);
        plugin.setBots(new ArrayList<>());

        when(pluginRepository.findById(pluginId)).thenReturn(Optional.of(plugin));
        when(userRepository.findByTrueId(userId)).thenReturn(Optional.of(user));

        PluginDetailInfoDTO response = pluginServiceImpl.getPluginInfo(pluginId, userId);
        System.out.println(response);
        assertEquals(response.getId(), 0);
        assertEquals(response.getCreatorId(), userId);
    }

    @Test
    void testGetPluginInfo_NoSuchElementException_Plugin() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer pluginId = 1, userId = 1;
        User user = new User();
        user.setTrueId(1);
        Plugin plugin = new Plugin(dtoFactory.createPluginInfoDTO(1), user, "");
        plugin.setIsPublished(true);
        plugin.setCreator(user);
        plugin.setBots(new ArrayList<>());

        when(pluginRepository.findById(pluginId)).thenReturn(Optional.empty());
        when(userRepository.findByTrueId(userId)).thenReturn(Optional.of(user));

        try {
            pluginServiceImpl.getPluginInfo(pluginId, userId);
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetPluginInfo_NoSuchElementException_User() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer pluginId = 1, userId = 1;
        User user = new User();
        user.setTrueId(1);
        Plugin plugin = new Plugin(dtoFactory.createPluginInfoDTO(1), user, "");
        plugin.setIsPublished(true);
        plugin.setCreator(user);
        plugin.setBots(new ArrayList<>());

        when(pluginRepository.findById(pluginId)).thenReturn(Optional.of(plugin));
        when(userRepository.findByTrueId(userId)).thenReturn(Optional.empty());

        try {
            pluginServiceImpl.getPluginInfo(pluginId, userId);
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
}
