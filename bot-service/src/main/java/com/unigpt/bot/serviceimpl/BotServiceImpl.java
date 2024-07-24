package com.unigpt.bot.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unigpt.bot.dto.BotBriefInfoDTO;
import com.unigpt.bot.dto.BotDetailInfoDTO;
import com.unigpt.bot.dto.BotEditInfoDTO;
import com.unigpt.bot.dto.GetBotsOkResponseDTO;
import com.unigpt.bot.dto.GetCommentsOkResponseDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.model.Bot;
import com.unigpt.bot.model.User;
import com.unigpt.bot.repository.BotRepository;
import com.unigpt.bot.repository.UserRepository;
import com.unigpt.bot.service.BotService;

@Service
public class BotServiceImpl implements BotService {

    private final BotRepository botRepository;
    private final UserRepository userRepository;

    public BotServiceImpl(BotRepository botRepository, UserRepository userRepository) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
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
            // TODO: 将机器人从用户的 usedBots 列表中删除
            // // 如果均不是，检查是否是 bot 是否在用户的 usedBots 列表中，如有则删除
            // if (user.getUsedBots().contains(bot)) {
            // user.getUsedBots().remove(bot);
            // userRepository.save(user);
            // }
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
    public ResponseDTO createBot(BotEditInfoDTO dto, String token) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBot'");
    }

    @Override
    public ResponseDTO updateBot(Integer id, BotEditInfoDTO dto, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBot'");
    }

    @Override
    public ResponseDTO likeBot(Integer id, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likeBot'");
    }

    @Override
    public ResponseDTO dislikeBot(Integer id, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dislikeBot'");
    }

    @Override
    public ResponseDTO starBot(Integer id, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'starBot'");
    }

    @Override
    public ResponseDTO unstarBot(Integer id, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unstarBot'");
    }

    @Override
    public GetCommentsOkResponseDTO getComments(Integer id, Integer page, Integer pageSize) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComments'");
    }

    @Override
    public ResponseDTO createComment(Integer id, String token, String content) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createComment'");
    }

}
