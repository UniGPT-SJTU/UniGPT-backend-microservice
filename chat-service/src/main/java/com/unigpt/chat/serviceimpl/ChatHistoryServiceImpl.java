package com.unigpt.chat.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.chat.dto.ChatDTO;
import com.unigpt.chat.dto.GetChatsOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;
import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.ChatType;
import com.unigpt.chat.model.History;
import com.unigpt.chat.model.Memory;
import com.unigpt.chat.model.MemoryItem;
import com.unigpt.chat.repository.ChatRepository;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.service.ChatHistoryService;

import dev.langchain4j.data.message.ChatMessageType;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final HistoryRepository historyRepository;
    private final MemoryRepository memoryRepository;
    private final ChatRepository chatRepository;

    public ChatHistoryServiceImpl(
            HistoryRepository historyRepository,
            MemoryRepository memoryRepository,
            ChatRepository chatRepository) {
        this.historyRepository = historyRepository;
        this.memoryRepository = memoryRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public GetChatsOkResponseDTO getChats(Integer userId, Integer historyId, Integer page, Integer pageSize)
            throws AuthenticationException {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("History not found for ID: " + historyId));

        if (!history.getUser().getId().equals(userId)) {
            throw new AuthenticationException("User not authorized to access this history");
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        List<ChatDTO> chats = chatRepository
                .findChatsByIsVisibleTrueAndHistory(history, pageable)
                .toList()
                .stream()
                .map(ChatDTO::new)
                .collect(Collectors.toList());

        Integer totalChatsCount = chatRepository.countByIsVisibleTrueAndHistory(history);
        return new GetChatsOkResponseDTO(totalChatsCount, chats);
    }

    @Override
    public List<PromptDTO> getPromptList(Integer userId, Integer historyid) throws AuthenticationException {
        History history = historyRepository
                .findById(historyid)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "History not found for ID: " + historyid));

        if (!history.getUser().getId().equals(userId)) {
            throw new AuthenticationException("User not authorized to access this history");
        }

        List<PromptDTO> promptList = history
                .getPromptKeyValuePairs()
                .entrySet()
                .stream()
                .map(
                        entry -> new PromptDTO(
                                entry.getKey(),
                                entry.getValue()))
                .collect(Collectors.toList());
        return promptList;
    }

    @Override
    public void deleteHistory(Integer userId, Integer historyId) throws Exception {
        History targetHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("History not found"));
        if (!targetHistory.getUser().getId().equals(userId)) {
            throw new AuthenticationException("unauthorized");
        }

        // 先删除memory，再删除history
        memoryRepository.deleteById(historyId);
        historyRepository.deleteById(historyId);
    }

    @Override
    public void deleteLastRoundOfChats(Integer historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("History not found for ID: " + historyId));
        Memory memory = memoryRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("Memory not found for ID: " + historyId));

        // 删除最后一轮的对话（包括Chat和MemoryItem）
        // 从后往前遍历，直到遇到第一个USER类型的对话

        List<Chat> chats = history.getChats();
        for (int i = chats.size() - 1; i >= 0; --i) {
            Chat chat = chats.remove(i);
            if (chat.getType() == ChatType.USER) {
                break;
            }
        }
        historyRepository.save(history);

        List<MemoryItem> memoryItems = memory.getMemoryItems();
        for (int i = memoryItems.size() - 1; i >= 0; --i) {
            MemoryItem memoryItem = memoryItems.remove(i);
            if (memoryItem.getType() == ChatMessageType.USER) {
                break;
            }
        }
        memoryRepository.save(memory);
    }

    @Override
    public void createChat(Integer historyId, String content, ChatType type) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("History not found for ID: " + historyId));

        Chat chat = new Chat(history, type, content, true);

        history.getChats().add(chat);
        historyRepository.save(history);
    }

    @Override
    public History getHistory(Integer historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NoSuchElementException("History not found for ID: " + historyId));
        return history;
    }

    @Override
    public void updateHistoryActiveTime(History history) {
        history.setLastActiveTime(new Date());
        historyRepository.save(history);
    }

}
