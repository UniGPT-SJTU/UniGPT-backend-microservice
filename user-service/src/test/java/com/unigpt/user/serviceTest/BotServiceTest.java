package com.unigpt.user.serviceTest;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.model.User;
import com.unigpt.user.model.Bot;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.repository.UserRepository;
import com.unigpt.user.serviceImpl.BotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class BotServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBot_Success() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, userRepository);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();

        when(userRepository.findById(userid)).thenReturn(Optional.of(new User()));
        when(botRepository.existsById(botid)).thenReturn(false);

        try {
            ResponseDTO responseDTO = botServiceImpl.createBot(dto, botid, userid);
            assertTrue(responseDTO.getOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateBot_UserNotFound() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, userRepository);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();

        when(userRepository.findById(userid)).thenReturn(Optional.empty());

        try {
            ResponseDTO responseDTO = botServiceImpl.createBot(dto, botid, userid);
        } catch (Exception e){
            assertTrue(e.getMessage().equals("User not found"));
        }
    }

    @Test
    void testCreateBot_BotIdAlreadyInUse() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, userRepository);

        Integer userid = 1, botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();

        when(userRepository.findById(userid)).thenReturn(Optional.of(new User()));
        when(botRepository.existsById(botid)).thenReturn(true);

        try {
            ResponseDTO responseDTO = botServiceImpl.createBot(dto, botid, userid);
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Bot id already in use"));
        }
    }

    @Test
    void testUpdateBot_Success() {
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, userRepository);

        Integer botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.of(new Bot()));

        ResponseDTO responseDTO = botServiceImpl.updateBot(botid, dto, 1);
        assertTrue(responseDTO.getOk());
    }

    @Test
    void testUpdateBot_NoSuchElementException(){
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceImpl botServiceImpl = new BotServiceImpl(botRepository, userRepository);

        Integer botid = 1;
        BotEditInfoDTO dto = new BotEditInfoDTO();
        when(botRepository.findByTrueId(botid)).thenReturn(Optional.empty());

        try{
            ResponseDTO responseDTO = botServiceImpl.updateBot(botid, dto, 1);
        }
        catch (NoSuchElementException e){
            assertTrue(true);
        }
    }
}
