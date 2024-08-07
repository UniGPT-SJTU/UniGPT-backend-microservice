package com.unigpt.chat.serviceTest;


import com.unigpt.chat.client.BotServiceClient;
import com.unigpt.chat.client.UserServiceClient;
import com.unigpt.chat.dto.*;
import com.unigpt.chat.model.*;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.repository.ChatRepository;
import com.unigpt.chat.serviceimpl.ChatHistoryServiceImpl;
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


public class ChatHistoryServiceTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChats_Success() throws AuthenticationException {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(1);
        History history = new History();
        history.setUser(user);

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));
        when(chatRepository.findChatsByIsVisibleTrueAndHistory(history, PageRequest.of(0, 10)))
                .thenReturn(Page.empty());
        when(chatRepository.countByIsVisibleTrueAndHistory(history)).thenReturn(0);

        GetChatsOkResponseDTO response = chatHistoryService.getChats(1, 1, 0, 10);
        assertEquals(0, response.getTotal());
    }

    @Test
    void testGetChats_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.getChats(1, 1, 0, 10);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testGetChats_AuthenticationException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(2);
        History history = new History();
        history.setUser(user);

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        try{
            chatHistoryService.getChats(1, 1, 0, 10);
            fail("Should throw AuthenticationException");
        } catch (AuthenticationException e){
            assertEquals("User not authorized to access this history", e.getMessage());
        } catch (Exception e){
            fail("Should throw AuthenticationException");
        }
    }

    @Test
    void testGetPromptList_Success() throws AuthenticationException {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(1);
        History history = new History();
        history.setUser(user);
        Map<String, String> promptKeyValuePairs = new HashMap<>();
        promptKeyValuePairs.put("key1", "value1");
        promptKeyValuePairs.put("key2", "value2");
        history.setPromptKeyValuePairs(promptKeyValuePairs);

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        List<PromptDTO> promptList = chatHistoryService.getPromptList(1, 1);
        assertEquals(2, promptList.size());
        assertEquals("key1", promptList.get(0).getPromptKey());
        assertEquals("value1", promptList.get(0).getPromptValue());
        assertEquals("key2", promptList.get(1).getPromptKey());
        assertEquals("value2", promptList.get(1).getPromptValue());
    }

    @Test
    void testGetPromptList_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.getPromptList(1, 1);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testGetPromptList_AuthenticationException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(2);
        History history = new History();
        history.setUser(user);

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        try{
            chatHistoryService.getPromptList(1, 1);
            fail("Should throw AuthenticationException");
        } catch (AuthenticationException e){
            assertEquals("User not authorized to access this history", e.getMessage());
        } catch (Exception e){
            fail("Should throw AuthenticationException");
        }
    }

    @Test
    void testDeleteHistory_Success() throws Exception {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(1);
        History history = new History();
        history.setUser(user);
        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        try {
            chatHistoryService.deleteHistory(1, 1);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void testDeleteHistory_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.deleteHistory(1, 1);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testDeleteHistory_AuthenticationException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        User user = new User();
        user.setId(2);
        History history = new History();
        history.setUser(user);
        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        try{
            chatHistoryService.deleteHistory(1, 1);
            fail("Should throw AuthenticationException");
        } catch (AuthenticationException e){
            assertEquals("unauthorized", e.getMessage());
        } catch (Exception e){
            fail("Should throw AuthenticationException");
        }
    }

    @Test
    void testDeleteLastRoundOfChats_Success() throws Exception {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        History history = new History();
        Chat chat = new Chat();
        chat.setType(ChatType.USER);
        history.setChats(new ArrayList<>(Arrays.asList(chat)));

        Memory memory = new Memory();
        MemoryItem memoryItem = new MemoryItem();
        memoryItem.setType(ChatMessageType.USER);
        memory.setMemoryItems(new ArrayList<>(Arrays.asList(memoryItem)));

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));
        when(memoryRepository.findById(1)).thenReturn(Optional.of(memory));


        try {
            chatHistoryService.deleteLastRoundOfChats(1);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void testDeleteLastRoundOfChats_history_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.deleteLastRoundOfChats(1);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testDeleteLastRoundOfChats_memory_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        History history = new History();
        Chat chat = new Chat();
        chat.setType(ChatType.USER);
        history.setChats(new ArrayList<>(Arrays.asList(chat)));

        when(historyRepository.findById(1)).thenReturn(Optional.of(history));
        when(memoryRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.deleteLastRoundOfChats(1);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("Memory not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testCreateChat_Success() throws AuthenticationException {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        History history = new History();
        history.setChats(new ArrayList<>());
        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        try {
            chatHistoryService.createChat(1, "content", ChatType.USER);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void testCreateChat_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.createChat(1, "content", ChatType.USER);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testGetHistory_Success() throws AuthenticationException {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        History history = new History();
        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        History result = chatHistoryService.getHistory(1);
        assertEquals(history, result);
    }

    @Test
    void testGetHistory_NoSuchElementException(){
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        try{
            chatHistoryService.getHistory(1);
            fail("Should throw NoSuchElementException");
        } catch (NoSuchElementException e){
            assertEquals("History not found for ID: 1", e.getMessage());
        } catch (Exception e){
            fail("Should throw NoSuchElementException");
        }
    }

    @Test
    void testUpdateHistoryActiveTime_Success() throws AuthenticationException {
        HistoryRepository historyRepository = mock(HistoryRepository.class);
        MemoryRepository memoryRepository = mock(MemoryRepository.class);
        ChatRepository chatRepository = mock(ChatRepository.class);
        ChatHistoryServiceImpl chatHistoryService = new ChatHistoryServiceImpl(historyRepository, memoryRepository, chatRepository);

        History history = new History();

        try {
            chatHistoryService.updateHistoryActiveTime(history);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
}
