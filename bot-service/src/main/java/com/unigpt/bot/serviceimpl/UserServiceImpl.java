package com.unigpt.bot.serviceimpl;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.unigpt.bot.dto.BotBriefInfoDTO;
import com.unigpt.bot.dto.GetBotsOkResponseDTO;
import com.unigpt.bot.dto.UpdateUserInfoRequestDTO;
import com.unigpt.bot.model.Bot;
import com.unigpt.bot.model.User;
import com.unigpt.bot.repository.UserRepository;
import com.unigpt.bot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(Integer id, UpdateUserInfoRequestDTO dto) {
        if (userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User(id, dto.getName(), dto.getAvatar());
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(Integer targetUserId,
            UpdateUserInfoRequestDTO updateUserInfoRequestDTO)
            throws AuthenticationException {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        targetUser.setName(updateUserInfoRequestDTO.getName());
        targetUser.setAvatar(updateUserInfoRequestDTO.getAvatar());

        userRepository.save(targetUser);
    }

    @Override
    public GetBotsOkResponseDTO getStarredBots(Integer userId, Integer page, Integer pageSize) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        PageRequest pageable = PageRequest.of(page, pageSize);
        List<Bot> starredBots = userRepository
                .findStarredBotsByUserId(userId, pageable)
                .toList();

        List<BotBriefInfoDTO> bots = starredBots.stream()
                .map(bot -> new BotBriefInfoDTO(bot))
                .collect(Collectors.toList());
        Collections.reverse(bots);

        return new GetBotsOkResponseDTO(userRepository.countStarredBotsByUserId(userId), bots);
    }

    @Override
    public GetBotsOkResponseDTO getCreatedBots(Integer userid, Integer page, Integer pageSize) {
        userRepository.findById(userid)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        PageRequest pageable = PageRequest.of(page, pageSize);
        List<Bot> createdBots = userRepository
                .findCreatedBotsByUserId(userid, pageable)
                .toList();
        List<BotBriefInfoDTO> bots = createdBots.stream()
                .map(bot -> new BotBriefInfoDTO(bot))
                .collect(Collectors.toList());

        Collections.reverse(bots);

        return new GetBotsOkResponseDTO(userRepository.countCreatedBotsByUserId(userid), bots);
    }

}
