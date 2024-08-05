package com.unigpt.bot.serviceimpl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.bot.client.ChatServiceClient;
import com.unigpt.bot.client.PluginServiceClient;
import com.unigpt.bot.client.UserServiceClient;
import com.unigpt.bot.dto.BotBriefInfoDTO;
import com.unigpt.bot.dto.BotDetailInfoDTO;
import com.unigpt.bot.dto.BotEditInfoDTO;
import com.unigpt.bot.dto.CommentDTO;
import com.unigpt.bot.dto.GetBotsOkResponseDTO;
import com.unigpt.bot.dto.GetCommentsOkResponseDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.model.Bot;
import com.unigpt.bot.model.ChatType;
import com.unigpt.bot.model.Comment;
import com.unigpt.bot.model.Plugin;
import com.unigpt.bot.model.PromptChat;
import com.unigpt.bot.model.User;
import com.unigpt.bot.repository.BotRepository;
import com.unigpt.bot.repository.CommentRepository;
import com.unigpt.bot.repository.PluginRepository;
import com.unigpt.bot.repository.PromptChatRepository;
import com.unigpt.bot.repository.UserRepository;
import com.unigpt.bot.service.BotService;

@Service
public class BotServiceImpl implements BotService {

    private final BotRepository botRepository;
    private final UserRepository userRepository;
    private final PromptChatRepository promptChatRepository;
    private final PluginRepository pluginRepository;
    private final CommentRepository commentRepository;

    private final UserServiceClient userServiceClient;
    private final ChatServiceClient chatServiceClient;
    private final PluginServiceClient pluginServiceClient;

    public BotServiceImpl(
            BotRepository botRepository,
            UserRepository userRepository,
            PromptChatRepository promptChatRepository,
            PluginRepository pluginRepository,
            CommentRepository commentRepository,
            UserServiceClient userServiceClient,
            ChatServiceClient chatServiceClient,
            PluginServiceClient pluginServiceClient) {

        this.botRepository = botRepository;
        this.userRepository = userRepository;
        this.promptChatRepository = promptChatRepository;
        this.pluginRepository = pluginRepository;
        this.commentRepository = commentRepository;
        this.userServiceClient = userServiceClient;
        this.chatServiceClient = chatServiceClient;
        this.pluginServiceClient = pluginServiceClient;
    }

    private List<BotBriefInfoDTO> getBots(String q, Pageable pageable, String order) {
        Page<Bot> page;
        if (order.equals("latest")) {
            page = q.isEmpty() ? botRepository.findBotsByIsPublishedOrderByIdDesc(pageable)
                    : botRepository.findBotsByNameContainsAndIsPublishedOrderByIdDesc(q, pageable);
        } else if (order.equals("like")) {
            page = q.isEmpty() ? botRepository.findBotsByIsPublishedOrderByLikeNumberDesc(pageable)
                    : botRepository.findBotsByNameContainsAndIsPublishedOrderByLikeNumberDesc(q, pageable);
        } else {
            throw new IllegalArgumentException("Invalid order parameter");
        }
        return page.stream().map(BotBriefInfoDTO::new).toList();
    }

    private Integer getBotsTotalCount(String q) {
        return q.isEmpty() ? botRepository.countByIsPublished()
                : botRepository.countByNameContainsAndIsPublished(q);
    }

    @Override
    public GetBotsOkResponseDTO getBots(String q, String order, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<BotBriefInfoDTO> bots = getBots(q, pageable, order);
        Integer botsTotalCount = getBotsTotalCount(q);
        return new GetBotsOkResponseDTO(botsTotalCount, bots);
    }

    @Override
    @Cacheable(value = "botBriefInfoMicro", key = "{#userId,#isAdmin,#botId}")
    public BotBriefInfoDTO getBotBriefInfo(Integer userId, Boolean isAdmin, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        Boolean asCreator = bot.getCreator().getId().equals(userId);
        if (!bot.getIsPublished() && !asCreator) {
            // 如果bot未发布且请求用户不是bot的创建者，则抛出异常
            throw new NoSuchElementException("Bot not published for ID: " + botId);
        }
        return new BotBriefInfoDTO(bot, userId, isAdmin);
    }

    @Override
    public BotDetailInfoDTO getBotDetailInfo(Integer userId, Boolean isAdmin, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
        Boolean asCreator = bot.getCreator().getId().equals(userId);

        if (bot.getIsPublished() || asCreator || isAdmin) {
            return new BotDetailInfoDTO(bot, user, isAdmin);
        } else {
            // 检查是否是 bot 是否在用户的 usedBots 列表中，如有则删除
            userServiceClient.deleteBotFromUsedList(botId, userId);
            throw new NoSuchElementException("Bot not published for ID: " + botId);
        }
    }

    @Override
    public BotEditInfoDTO getBotEditInfo(Integer userId, Boolean isAdmin, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        Boolean asCreator = bot.getCreator().getId().equals(userId);

        if (!asCreator && !isAdmin) {
            throw new NoSuchElementException("Bot not published for ID: " + botId);
        }

        return new BotEditInfoDTO(bot);
    }

    @Override
    public ResponseDTO createBot(Integer userId, BotEditInfoDTO dto) throws Exception {
        // 根据token获取用户
        User creatorUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));

        int promptChatSize = dto.getPromptChats().size();
        if (promptChatSize < 1) {
            // 提示词模板列表不能为空
            throw new BadRequestException("Prompt chats should not be empty");
        }

        if (dto.getPromptChats().get(promptChatSize - 1).getType() != ChatType.USER) {
            // 最后一个提示词模板应该是用户类型
            throw new BadRequestException("Last prompt chat should be user type");
        }

        // 创建promptChats列表并保存到数据库
        List<PromptChat> promptChats = dto
                .getPromptChats()
                .stream()
                .map(PromptChat::new)
                .collect(Collectors.toList());
        promptChatRepository.saveAll(promptChats);

        // 创建bot的plugin列表
        // 对于每个plugin, 获取其中的id,使用findById方法获取plugin对象, 如果不存在则抛出异常
        // 如果存在则将plugin对象加入到plugin列表中
        List<Plugin> plugins = dto.getPlugins().stream()
                .map(plugin -> pluginRepository.findById(plugin.getId())
                        .orElseThrow(() -> new NoSuchElementException("Plugin not found for ID: " + plugin.getId())))
                .collect(Collectors.toList());

        // 创建bot并保存到数据库
        Bot newBot = new Bot(dto, creatorUser);
        newBot.setPromptChats(promptChats);
        newBot.setPlugins(plugins);
        botRepository.save(newBot);

        // 更新用户的createBots列表
        creatorUser.getCreateBots().add(newBot);
        userRepository.save(creatorUser);

        String botId = String.valueOf(newBot.getId());

        // 向微服务发送请求，创建bot的冗余信息
        userServiceClient.createBot(newBot.getId(), dto.toUserServiceRequest());
        chatServiceClient.createBot(newBot.getId(), dto.toChatServiceRequest());
        pluginServiceClient.createBot(userId, dto.toPluginServiceRequest());

        return new ResponseDTO(true, botId);
    }

    @Autowired
    private CacheManager cacheManager;

    @Override
    public ResponseDTO updateBot(Integer userId, Boolean isAdmin, Integer botId, BotEditInfoDTO dto) {
        // 根据id获取bot
        Bot updatedBot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        if (!updatedBot.getCreator().getId().equals(userId) && !isAdmin) {
            throw new IllegalArgumentException("User not authorized to update bot");
        }

        // 删除原有的promptChats列表
        List<PromptChat> oldPromptChats = new ArrayList<>(updatedBot.getPromptChats());
        updatedBot.getPromptChats().clear();
        promptChatRepository.deleteAll(oldPromptChats);

        // 创建promptChats列表并保存到数据库
        List<PromptChat> promptChats = dto.getPromptChats().stream().map(PromptChat::new).collect(Collectors.toList());
        promptChatRepository.saveAll(promptChats);

        List<Plugin> plugins = dto.getPlugins().stream()
                .map(plugin -> pluginRepository.findById(plugin.getId())
                        .orElseThrow(() -> new NoSuchElementException("Plugin not found for ID: " + plugin.getId())))
                .collect(Collectors.toList());

        // 更新bot信息并保存到数据库
        updatedBot.updateInfo(dto);
        updatedBot.setPromptChats(promptChats);
        updatedBot.setPlugins(plugins);
        botRepository.save(updatedBot);

        // 向微服务发送请求，更新bot的冗余信息
        userServiceClient.updateBot(botId, dto.toUserServiceRequest());
        chatServiceClient.updateBot(botId, dto.toChatServiceRequest());
        pluginServiceClient.updateBot(botId, dto.toPluginServiceRequest());

        BotBriefInfoDTO briefInfo = new BotBriefInfoDTO(updatedBot);
        String key = String.format("%s,%s,%s",userId,isAdmin,botId);
        Objects.requireNonNull(cacheManager.getCache("botBriefInfoMicro")).put(key, briefInfo);

        return new ResponseDTO(true, "Bot updated successfully");
    }

    @Override
    public ResponseDTO likeBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
        if (bot.getLikeUsers().contains(user)) {
            return new ResponseDTO(false, "Bot already liked");
        }

        bot.setLikeNumber(bot.getLikeNumber() + 1);
        bot.getLikeUsers().add(user);
        user.getLikeBots().add(bot);

        botRepository.save(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot liked successfully");
    }

    @Override
    public ResponseDTO dislikeBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));

        if (!bot.getLikeUsers().contains(user)) {
            return new ResponseDTO(false, "Bot not liked yet");
        }

        bot.setLikeNumber(bot.getLikeNumber() - 1);
        bot.getLikeUsers().remove(user);
        user.getLikeBots().remove(bot);

        botRepository.save(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot disliked successfully");
    }

    @Override
    public ResponseDTO starBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
        if (bot.getStarUsers().contains(user)) {
            return new ResponseDTO(false, "Bot already starred");
        }

        bot.setStarNumber(bot.getStarNumber() + 1);
        bot.getStarUsers().add(user);
        user.getStarBots().add(bot);
        botRepository.save(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot starred successfully");
    }

    @Override
    public ResponseDTO unstarBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
        if (!bot.getStarUsers().contains(user)) {
            return new ResponseDTO(false, "Bot not starred yet");
        }

        bot.setStarNumber(bot.getStarNumber() - 1);
        bot.getStarUsers().remove(user);
        user.getStarBots().remove(bot);

        botRepository.save(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot unstarred successfully");
    }

    @Override
    public GetCommentsOkResponseDTO getComments(Integer botId, Integer page, Integer pageSize) {
        botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

        Pageable pageable = PageRequest.of(page, pageSize);
        List<CommentDTO> comments = commentRepository.findByBotIdOrderByTimeDesc(botId, pageable)
                .stream().map(CommentDTO::new).toList();
        Integer commentsTotalCount = commentRepository.countByBotId(botId);
        return new GetCommentsOkResponseDTO(commentsTotalCount, comments);
    }

    @Override
    public ResponseDTO createComment(Integer userId, Integer botId, String content) {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userId));
        Comment newComment = new Comment(content, new Date(), user, bot);
        bot.getComments().add(newComment);
        botRepository.save(bot);

        return new ResponseDTO(true, "Comment created successfully");
    }

}
