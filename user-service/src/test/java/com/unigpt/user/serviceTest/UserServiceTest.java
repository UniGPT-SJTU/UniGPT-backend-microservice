package com.unigpt.user.serviceTest;

import com.unigpt.user.client.BotServiceClient;
import com.unigpt.user.client.ChatServiceClient;
import com.unigpt.user.client.PluginServiceClient;
import com.unigpt.user.dto.*;
import com.unigpt.user.model.User;
import com.unigpt.user.model.Bot;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.repository.UserRepository;
import com.unigpt.user.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        String email = "", account = "", name = "";
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        when(userRepository.findByAccount(account)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        when(botServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        when(chatServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        when(pluginServiceClient.createUser(user.getId(), user.getName()))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        Integer id = userServiceImpl.createUser(email, account, name);
        assertEquals(id, user.getId());
    }

    @Test
    void testCreateUser_IsPresent() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        String email = "", account = "", name = "";
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        when(userRepository.findByAccount(account)).thenReturn(Optional.of(user));

        Integer id = userServiceImpl.createUser(email, account, name);
        assertEquals(id, user.getId());
    }

    @Test
    void testCreateUser_BotRuntimeError(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        String email = "", account = "", name = "";
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        when(userRepository.findByAccount(account)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        when(botServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Bot runtime error")));

        try {
            userServiceImpl.createUser(email, account, name);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Failed to create user in bot service");
        }
    }

    @Test
    void testCreateUser_ChatRuntimeError(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        String email = "", account = "", name = "";
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        when(userRepository.findByAccount(account)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        when(botServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        when(chatServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Chat runtime error")));

        try {
            userServiceImpl.createUser(email, account, name);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Failed to create user in chat service");
        }
    }

    @Test
    void testCreateUser_PluginRuntimeError(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        String email = "", account = "", name = "";
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        when(userRepository.findByAccount(account)).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        when(botServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        when(chatServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User created successfully")));

        when(pluginServiceClient.createUser(user.getId(), user.getName()))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Plugin runtime error")));

        try {
            userServiceImpl.createUser(email, account, name);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Failed to create user in plugin service");
        }
    }

    @Test
    void testFindUserById_Success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User foundUser = userServiceImpl.findUserById(id);
        assertEquals(foundUser.getId(), user.getId());
    }

    @Test
    void testFindUserById_NoSuchElementException() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        try {
            userServiceImpl.findUserById(id);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "User not found for ID: " + id);
        }
    }

    @Test
    void testUpdateUserInfo_Success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;
        User user = new User();
        user.setId(id);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(botServiceClient.updateUser(id, new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User updated successfully")));
        when(chatServiceClient.updateUser(id, new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User updated successfully")));

        userServiceImpl.updateUserInfo(id, userUpdateDTO);
    }

    @Test
    void testUpdateUserInfo_NoSuchElementException() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        try {
            userServiceImpl.updateUserInfo(id, userUpdateDTO);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "User not found for ID: " + id);
        }
    }

    @Test
    void testUpdateUserInfo_BotRuntimeError() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;
        User user = new User();
        user.setId(id);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(botServiceClient.updateUser(id, new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Bot runtime error")));

        try {
            userServiceImpl.updateUserInfo(id, userUpdateDTO);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Failed to update user in bot service");
        }
    }

    @Test
    void testUpdateUserInfo_ChatRuntimeError() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        Integer id = 1;
        User user = new User();
        user.setId(id);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(botServiceClient.updateUser(id, new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "User updated successfully")));
        when(chatServiceClient.updateUser(id, new UserUpdateRequestDTO(user)))
                .thenReturn(ResponseEntity.badRequest().body(new ResponseDTO(false, "Chat runtime error")));

        try {
            userServiceImpl.updateUserInfo(id, userUpdateDTO);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Failed to update user in chat service");
        }
    }

    @Test
    void testGetUsers_Success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        when(userRepository.findAll()).thenReturn(List.of(user));

        GetUsersOkResponseDTO getUsersOkResponseDTO = userServiceImpl.getUsers(0, 20, "", "");
        assertEquals(getUsersOkResponseDTO.getUsers().size(), 1);
    }

    @Test
    void testGetUsers_IdSuccess(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        user.setId(0);
        when(userRepository.findAll()).thenReturn(List.of(user));

        GetUsersOkResponseDTO getUsersOkResponseDTO = userServiceImpl.getUsers(0, 20, "id", "0");
        assertEquals(1, getUsersOkResponseDTO.getUsers().size());
    }

    @Test
    void testGetUsers_NumberFormatException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        user.setId(0);
        when(userRepository.findAll()).thenReturn(List.of(user));

        GetUsersOkResponseDTO getUsersOkResponseDTO = userServiceImpl.getUsers(0, 20, "id", "a");
        assertEquals(1, getUsersOkResponseDTO.getUsers().size());
    }

    @Test
    void testGetUsedBots_Success(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        GetBotsOkResponseDTO getBotsOkResponseDTO = userServiceImpl.getUsedBots(0, 0, 20);
        assertEquals(0, getBotsOkResponseDTO.getBots().size());
    }

    @Test
    void testGetUsedBots_NoSuchElementException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        when(userRepository.findById(0)).thenReturn(Optional.empty());

        try {
            userServiceImpl.getUsedBots(0, 0, 20);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "User not found for ID: 0");
        }
    }

    @Test
    void testUseBot_Success(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        Bot bot = new Bot();
        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(bot));
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        ResponseDTO responseDTO = userServiceImpl.useBot(0, 0);
        assertEquals(responseDTO.getOk(), true);
    }

    @Test
    void testUseBot_BotNoSuchElementException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        when(botRepository.findByTrueId(0)).thenReturn(Optional.empty());
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        try {
            userServiceImpl.useBot(0, 0);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Bot not found");
        }
    }

    @Test
    void testUseBot_UserNoSuchElementException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(new Bot()));
        when(userRepository.findById(0)).thenReturn(Optional.empty());

        try {
            userServiceImpl.useBot(0, 0);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    void testUseBot_BotAlreadyUsed(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        Bot bot = new Bot();
        user.getUsedBots().add(bot);
        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(bot));
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        ResponseDTO responseDTO = userServiceImpl.useBot(0, 0);
        assertEquals(responseDTO.getOk(), false);
        assertEquals(responseDTO.getMessage(), "Bot already used");
    }

    @Test
    void testDisuseBot_Success(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        Bot bot = new Bot();
        user.getUsedBots().add(bot);
        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(bot));
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        ResponseDTO responseDTO = userServiceImpl.disuseBot(0, 0);
        assertEquals(responseDTO.getOk(), true);
    }

    @Test
    void testDisuseBot_BotNoSuchElementException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        when(botRepository.findByTrueId(0)).thenReturn(Optional.empty());
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        try {
            userServiceImpl.disuseBot(0, 0);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Bot not found");
        }
    }

    @Test
    void testDisuseBot_UserNoSuchElementException(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(new Bot()));
        when(userRepository.findById(0)).thenReturn(Optional.empty());

        try {
            userServiceImpl.disuseBot(0, 0);
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    void testDisuseBot_BotNotUsed(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotRepository botRepository = Mockito.mock(BotRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        ChatServiceClient chatServiceClient = Mockito.mock(ChatServiceClient.class);
        PluginServiceClient pluginServiceClient = Mockito.mock(PluginServiceClient.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, botRepository, botServiceClient, chatServiceClient, pluginServiceClient);

        User user = new User();
        user.setName("");
        when(botRepository.findByTrueId(0)).thenReturn(Optional.of(new Bot()));
        when(userRepository.findById(0)).thenReturn(Optional.of(user));

        ResponseDTO responseDTO = userServiceImpl.disuseBot(0, 0);
        assertEquals(responseDTO.getOk(), false);
        assertEquals(responseDTO.getMessage(), "Bot wasn't used");
    }
}
