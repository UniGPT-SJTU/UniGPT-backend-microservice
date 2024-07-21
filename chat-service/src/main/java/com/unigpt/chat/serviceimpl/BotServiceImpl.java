package com.unigpt.chat.serviceimpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.chat.dto.CreateBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;
import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.History;
import com.unigpt.chat.model.Memory;
import com.unigpt.chat.model.MemoryItem;
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

    @Override
    public CreateBotHistoryOkResponseDTO createBotHistory(Integer userId, Integer id, List<PromptDTO> promptList) throws Exception {
        Bot bot = botRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + id));

        // 校验promptList与bot.promptKeys的对应关系
        int promptListSize = promptList.size();
        if (promptListSize != bot.getPromptKeys().size()) {
            throw new BadRequestException("Prompt list not match");
        }
        for (int i = 0; i < promptListSize; ++i) {
            if (!promptList.get(i).getPromptKey().equals(bot.getPromptKeys().get(i))) {
                throw new BadRequestException("Prompt list not match");
            }
        }

        User user = authService.getUserByToken(token);

        // 将对应 bot 加入用户的 usedBots 列表
        if (!user.getUsedBots().contains(bot)) {
            user.getUsedBots().add(bot);
            userRepository.save(user);
        } else {
            user.getUsedBots().remove(bot);
            user.getUsedBots().add(bot);
            userRepository.save(user);
        }

        // 將提示词列表转换为 key-value 对
        Map<String, String> promptKeyValuePairs = promptList.stream()
                .collect(Collectors.toMap(PromptDTO::getPromptKey, PromptDTO::getPromptValue));

        // 创建新的对话历史并保存到数据库
        History history = new History(
                user,
                bot,
                promptKeyValuePairs,
                bot.getLlmArgs());
        historyRepository.save(history);

        Memory memory = new Memory(history);
        memoryRepository.save(memory);

        // 將用户填写的表单内容与 bot 的 promptChats 进行模板插值，
        // 并保存到数据库
        List<Chat> interpolatedChats = bot.getPromptChats()
                .stream()
                .map(
                        promptChat -> new Chat(
                                history,
                                promptChat.getType(),
                                StringTemplateParser.interpolate(
                                        promptChat.getContent(),
                                        promptKeyValuePairs),
                                false)) // 前面的提示词用户不可见
                .collect(Collectors.toList());

        // 最后一条 chat 是用户的提问，设置为可见
        interpolatedChats.get(interpolatedChats.size() - 1).setIsVisible(true);

        // 将插值后的结果加入对话历史
        // 并保存到数据库
        history.getChats().addAll(interpolatedChats);
        historyRepository.save(history);

        memory.getMemoryItems().addAll(
                interpolatedChats.stream()
                        // TODO: 有可能的话，标记一下userAsk
                        .limit(interpolatedChats.size() - 1)
                        .map(chat -> new MemoryItem(chat, memory))
                        .collect(Collectors.toList()));
        memoryRepository.save(memory);

        // 将对话历史加入用户的 histories 列表
        // user.getHistories().add(history);
        // userRepository.save(user);
        return new CreateBotHistoryOkResponseDTO(
                true, "Chat history created successfully",
                history.getId(),
                interpolatedChats.get(interpolatedChats.size() - 1).getContent());
    }
}
