package com.unigpt.plugin.serviceTest;

import com.unigpt.plugin.Repository.BotRepository;
import com.unigpt.plugin.Repository.PluginRepository;
import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.factory.dtoFactory;
import com.unigpt.plugin.model.Bot;
import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.serviceImpl.BotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class botServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBot_Success() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, pluginRepository);

        Integer botid = 1;
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(1);
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.empty());
        // whatever plugin id is, pluginRepository.findById returns a plugin object
        for(int i = 0; i < dto.getPlugin_ids().size(); i++){
            when(pluginRepository.findById(dto.getPlugin_ids().get(i))).thenReturn(Optional.of(new Plugin()));
        }

        ResponseDTO responseDTO = botServiceImpl.createBot(dto, botid);
        assertTrue(responseDTO.getOk());
    }

    @Test
    void testCreateBot_Failure() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, pluginRepository);

        Integer botid = 1;
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(1);
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.of(new Bot()));

        ResponseDTO responseDTO = botServiceImpl.createBot(dto, botid);
        assertFalse(responseDTO.getOk());
    }

    @Test
    void testUpdateBot_Success() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, pluginRepository);

        Integer botid = 1;
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(1);
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.of(new Bot()));
        // whatever plugin id is, pluginRepository.findById returns a plugin object
        for(int i = 0; i < dto.getPlugin_ids().size(); i++){
            when(pluginRepository.findById(dto.getPlugin_ids().get(i))).thenReturn(Optional.of(new Plugin()));
        }

        ResponseDTO responseDTO = botServiceImpl.updateBot(dto, botid);
        assertTrue(responseDTO.getOk());
    }

    @Test
    void testUpdateBot_Failure() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, pluginRepository);

        Integer botid = 1;
        BotInfoDTO dto = dtoFactory.createBotInfoDTO(1);
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.empty());
        for (int i = 0; i < dto.getPlugin_ids().size(); i++) {
            when(pluginRepository.findById(dto.getPlugin_ids().get(i))).thenReturn(Optional.of(new Plugin()));
        }

        try{
            ResponseDTO responseDTO = botServiceImpl.updateBot(dto, botid);
        }
        catch (Exception e){
            assertTrue(e instanceof NoSuchElementException);
        }
    }

}
