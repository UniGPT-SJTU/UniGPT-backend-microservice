package com.unigpt.chat.serviceimpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.History;
import com.unigpt.chat.repository.BotRepository;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.service.BotService;

@Service
public class BotServiceImpl implements BotService {

    private final BotRepository botRepository;
    private final HistoryRepository historyRepository;

    public BotServiceImpl(BotRepository botRepository, HistoryRepository historyRepository) {
        this.botRepository = botRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public GetBotHistoryOkResponseDTO getBotHistory(Integer userId, Integer botId, Integer page,
            Integer pageSize) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

        Pageable pageable = PageRequest.of(page, pageSize);
        List<History> historyList = historyRepository
                .findHistoriesByBotAndUserId(pageable, bot, userId)
                .toList();

        // 按 history的最近活动时间排序
        Collections.sort(historyList, Comparator.comparing(History::getLatestChatTime).reversed());

        Integer totalHistoryCount = historyRepository.countByBotAndUserId(bot, userId);
        return new GetBotHistoryOkResponseDTO(totalHistoryCount, historyList);
    }
}
