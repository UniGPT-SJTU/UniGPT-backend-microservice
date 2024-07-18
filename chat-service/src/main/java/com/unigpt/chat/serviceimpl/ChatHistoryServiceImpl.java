package com.unigpt.chat.serviceimpl;

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
import com.unigpt.chat.model.History;
import com.unigpt.chat.repository.ChatRepository;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.service.ChatHistoryService;

@Service
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final HistoryRepository historyRepository;
    private final MemoryRepository memoryRepository;
    private final ChatRepository chatRepository;

    public ChatHistoryServiceImpl(
            HistoryRepository historyRepository,
            MemoryRepository memoryRepository,
            ChatRepository chatRepository
    ) {
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
                .findByIsVisibleTrueAndHistory(history, pageable)
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

}
