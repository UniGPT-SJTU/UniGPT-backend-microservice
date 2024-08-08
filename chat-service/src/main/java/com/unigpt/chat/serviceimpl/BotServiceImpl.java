package com.unigpt.chat.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.chat.client.BotServiceClient;
import com.unigpt.chat.client.UserServiceClient;
import com.unigpt.chat.dto.BotEditInfoDTO;
import com.unigpt.chat.dto.BotHistoryInfoDTO;
import com.unigpt.chat.dto.CreateBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;
import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.History;
import com.unigpt.chat.model.Memory;
import com.unigpt.chat.model.MemoryItem;
import com.unigpt.chat.model.User;
import com.unigpt.chat.repository.BotRepository;
import com.unigpt.chat.repository.HistoryRepository;
import com.unigpt.chat.repository.MemoryRepository;
import com.unigpt.chat.repository.UserRepository;
import com.unigpt.chat.service.BotService;
import com.unigpt.chat.utils.StringTemplateParser;

@Service
public class BotServiceImpl implements BotService {

        private final BotRepository botRepository;
        private final HistoryRepository historyRepository;
        private final MemoryRepository memoryRepository;
        private final UserRepository userRepository;
        private final BotServiceClient botServiceClient;
        private final UserServiceClient userServiceClient;

        public BotServiceImpl(
                        BotRepository botRepository,
                        HistoryRepository historyRepository,
                        MemoryRepository memoryRepository,
                        UserRepository userRepository,
                        BotServiceClient botServiceClient,
                        UserServiceClient userServiceClient) {

                this.botRepository = botRepository;
                this.historyRepository = historyRepository;
                this.memoryRepository = memoryRepository;
                this.userRepository = userRepository;
                this.botServiceClient = botServiceClient;
                this.userServiceClient = userServiceClient;
        }

        @Override
        public GetBotHistoryOkResponseDTO getBotHistory(Integer userId, Integer botId, Integer page,
                        Integer pageSize) {
                Bot bot = botRepository.findById(botId)
                                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

                Pageable pageable = PageRequest.of(page, pageSize);
                List<History> historyList = new ArrayList<>(historyRepository
                                .findHistoriesByBotAndUserId(pageable, bot, userId)
                                .toList());

                // 按 history的最近活动时间排序
                Collections.sort(historyList, Comparator.comparing(History::getLatestChatTime).reversed());

                Integer totalHistoryCount = historyRepository.countByBotAndUserId(bot, userId);
                return new GetBotHistoryOkResponseDTO(totalHistoryCount, historyList);
        }

        @Override
        public CreateBotHistoryOkResponseDTO createBotHistory(Integer userId, Integer botId, List<PromptDTO> promptList)
                        throws Exception {
                Bot bot = botRepository.findById(botId)
                                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

                BotHistoryInfoDTO botHistoryInfo = botServiceClient.getBotHistoryInfo(botId);

                int promptListSize = promptList.size();
                if (promptListSize != botHistoryInfo.getPromptKeys().size()) {
                        throw new BadRequestException("Prompt list not match");
                }
                for (int i = 0; i < promptListSize; ++i) {
                        if (!promptList.get(i).getPromptKey().equals(botHistoryInfo.getPromptKeys().get(i))) {
                                throw new BadRequestException("Prompt list not match");
                        }
                }

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new AuthenticationException("User not found"));

                // 将对应 bot 加入用户的 usedBots 列表
                userServiceClient.updateUsedBots(userId, botId);
                // if (!user.getUsedBots().contains(bot)) {
                // user.getUsedBots().add(bot);
                // userRepository.save(user);
                // } else {
                // user.getUsedBots().remove(bot);
                // user.getUsedBots().add(bot);
                // userRepository.save(user);
                // }

                // 將提示词列表转换为 key-value 对
                Map<String, String> promptKeyValuePairs = promptList.stream()
                                .collect(Collectors.toMap(PromptDTO::getPromptKey, PromptDTO::getPromptValue));

                // 创建新的对话历史并保存到数据库
                History history = new History(
                                user,
                                bot,
                                promptKeyValuePairs,
                                botHistoryInfo.getLlmArgs());
                historyRepository.save(history);

                Memory memory = new Memory(history);
                memoryRepository.save(memory);

                // 將用户填写的表单内容与 bot 的 promptChats 进行模板插值，
                // 并保存到数据库
                List<Chat> interpolatedChats = botHistoryInfo.getPromptChats()
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

        public void createBot(Integer userId, Integer botId, BotEditInfoDTO dto) throws Exception {
                // 根据token获取用户
                User creatorUser = userRepository.findById(userId)
                                .orElseThrow(() -> new AuthenticationException("User not found"));
                botRepository.findById(botId).ifPresent(bot -> {
                        throw new RuntimeException("Bot already exists");
                });

                // 创建bot并保存到数据库
                Bot newBot = new Bot(botId, dto, creatorUser);
                botRepository.save(newBot);
        }

        @Override
        public void updateBot(Integer userId, Boolean isAdmin, Integer id, BotEditInfoDTO dto) throws Exception {
                // 根据id获取bot
                Bot updatedBot = botRepository.findById(id)
                                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + id));

                if (!updatedBot.getCreator().getId().equals(userId) && !isAdmin) {
                        // 如果用户不是bot的创建者且不是管理员，则无权更新bot
                        throw new IllegalArgumentException("User not authorized to update bot");
                }

                // 更新bot信息并保存到数据库
                updatedBot.updateInfo(dto);
                botRepository.save(updatedBot);
        }
}
