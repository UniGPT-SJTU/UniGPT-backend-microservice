package com.unigpt.user.serviceImpl;

import com.unigpt.user.client.BotServiceClient;
import com.unigpt.user.client.ChatServiceClient;
import com.unigpt.user.client.PluginServiceClient;
import com.unigpt.user.dto.*;
import com.unigpt.user.model.User;
import com.unigpt.user.model.Bot;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.repository.UserRepository;
import com.unigpt.user.service.UserService;
import com.unigpt.user.utils.PaginationUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BotRepository  botRepository;
    private final BotServiceClient botServiceClient;
    private final ChatServiceClient chatServiceClient;
    private final PluginServiceClient pluginServiceClient;

    public UserServiceImpl(
            UserRepository userRepository,
            BotRepository  botRepository,
            BotServiceClient botServiceClient,
            ChatServiceClient chatServiceClient,
            PluginServiceClient pluginServiceClient) {
        this.userRepository = userRepository;
        this.botRepository = botRepository;
        this.botServiceClient = botServiceClient;
        this.chatServiceClient = chatServiceClient;
        this.pluginServiceClient = pluginServiceClient;
    }

    public Integer createUser(String email, String account, String name){
        // Check if account already exists
        Optional<User> optionalUser = userRepository.findByAccount(account);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getId();
        }

        User user = new User();
        user.setEmail(email);
        user.setAccount(account);
        user.setName(name);

        if(botServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user))
                .getStatusCode().isError())
            throw new RuntimeException("Failed to create user in bot service");

        if(chatServiceClient.createUser(user.getId(), new UserUpdateRequestDTO(user))
                .getStatusCode().isError())
            throw new RuntimeException("Failed to create user in chat service");

        if(pluginServiceClient.createUser(user.getId(), user.getName())
                .getStatusCode().isError())
            throw new RuntimeException("Failed to create user in plugin service");

        userRepository.save(user);
        return user.getId();
    }

    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User not found for ID: " + id);
        }
        return optionalUser.get();
    }

    public void updateUserInfo(
            Integer id,
            UserUpdateDTO userUpdateDTO) {
        User targetUser = findUserById(id);

        targetUser.setName(userUpdateDTO.getName());
        targetUser.setAvatar(userUpdateDTO.getAvatar());
        targetUser.setDescription(userUpdateDTO.getDescription());

        if(botServiceClient.updateUser(targetUser.getId(), new UserUpdateRequestDTO(targetUser))
                .getStatusCode().isError())
            throw new RuntimeException("Failed to update user in bot service");

        if(chatServiceClient.updateUser(targetUser.getId(), new UserUpdateRequestDTO(targetUser))
                .getStatusCode().isError())
            throw new RuntimeException("Failed to update user in chat service");

        userRepository.save(targetUser);
    }

    // type : id || name
    // q: keyword
    public GetUsersOkResponseDTO getUsers(Integer page, Integer pagesize, String type, String q) {
        List<User> users = userRepository.findAll();
        List<UserBriefInfoDTO> userBriefInfoDTOs;
        System.out.println("type: " + type + " q: " + q);
        if (type.equals("id")) {
            Integer id;
            try {
                id = Integer.parseInt(q);
            } catch (NumberFormatException e) {
                userBriefInfoDTOs = users.stream()
                        .map(UserBriefInfoDTO::new)
                        .collect(Collectors.toList());
                return new GetUsersOkResponseDTO(userBriefInfoDTOs.size(),
                        PaginationUtils.paginate(userBriefInfoDTOs, page, pagesize));
            }
            userBriefInfoDTOs = users.stream()
                    .filter(user -> user.getId() == id)
                    .map(UserBriefInfoDTO::new)
                    .collect(Collectors.toList());
        } else {
            userBriefInfoDTOs = users.stream()
                    .filter(user -> user.getName().contains(q))
                    .map(UserBriefInfoDTO::new)
                    .collect(Collectors.toList());
        }

        return new GetUsersOkResponseDTO(userBriefInfoDTOs.size(),
                PaginationUtils.paginate(userBriefInfoDTOs, page, pagesize));
    }


    public GetBotsOkResponseDTO getUsedBots(Integer userid, Integer page, Integer pageSize) {
        User user = findUserById(userid);

        List<Bot> usedBots = user.getUsedBots();
        Collections.reverse(usedBots);

        List<BotBriefInfoDTO> bots = usedBots.stream()
                .map(bot -> new BotBriefInfoDTO(bot.getTrueId(), bot.getName(), bot.getDescription(), bot.getAvatar(),
                        bot.getCreator().equals(user)))
                .collect(Collectors.toList());

        return new GetBotsOkResponseDTO(bots.size(), PaginationUtils.paginate(bots, page, pageSize));
    }
    
    public ResponseDTO useBot(Integer botId, Integer userId){
        Bot bot = botRepository.findByTrueId(botId).orElseThrow(() -> new NoSuchElementException("Bot not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (user.getUsedBots().contains(bot)) {
            return new ResponseDTO(false, "Bot already used");
        }
        user.getUsedBots().add(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot used successfully");
    }

    public ResponseDTO disuseBot(Integer botId, Integer userId){
        Bot bot = botRepository.findByTrueId(botId).orElseThrow(() -> new NoSuchElementException("Bot not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!user.getUsedBots().contains(bot)) {
            return new ResponseDTO(false, "Bot wasn't used");
        }
        user.getUsedBots().remove(bot);
        userRepository.save(user);
        return new ResponseDTO(true, "Bot disused successfully");
    }
//
//    // TODO: 修改BotBriefInfoDTO.asCreator
//    public GetBotsOkResponseDTO getStarredBots(Integer userid, String token, Integer page, Integer pageSize)
//            throws AuthenticationException {
//        User user = findUserById(userid);
//
//        List<Bot> starredBots = user.getStarBots();
//        Collections.reverse(starredBots);
//
//        List<BotBriefInfoDTO> bots = starredBots.stream()
//                .map(bot -> new BotBriefInfoDTO(bot.getId(), bot.getName(), bot.getDescription(), bot.getAvatar(),
//                        bot.getCreator().equals(user), user.getAsAdmin()))
//                .collect(Collectors.toList());
//
//        return new GetBotsOkResponseDTO(bots.size(), PaginationUtils.paginate(bots, page, pageSize));
//    }
//
//    // TODO: 修改BotBriefInfoDTO.asCreator
//    public GetBotsOkResponseDTO getCreatedBots(Integer userid, String token, Integer page, Integer pageSize) {
//        User user = findUserById(userid);
//        List<Bot> createdBots = user.getCreateBots();
//        Collections.reverse(createdBots);
//
//        List<BotBriefInfoDTO> bots = createdBots.stream()
//                .map(bot -> new BotBriefInfoDTO(bot.getId(), bot.getName(), bot.getDescription(), bot.getAvatar(),
//                        bot.getCreator().equals(user), user.getAsAdmin()))
//                .collect(Collectors.toList());
//
//        return new GetBotsOkResponseDTO(bots.size(), PaginationUtils.paginate(bots, page, pageSize));
//    }
//

//    public void setBanUser(Integer id, String token, Boolean state) throws AuthenticationException {
//        User requestUser;
//        try {
//            requestUser = authService.getUserByToken(token);
//        } catch (NoSuchElementException e) {
//            throw new AuthenticationException("Unauthorized to ban user");
//        }
//
//        if (!requestUser.getAsAdmin()) {
//            throw new AuthenticationException("Unauthorized to ban user");
//        }
//        User targetUser = findUserById(id);
//        targetUser.setDisabled(state);
//        userRepository.save(targetUser);
//    }
//
//    public Boolean getBanState(Integer id, String token) throws AuthenticationException {
//        User requestUser;
//        try {
//            requestUser = authService.getUserByToken(token);
//        } catch (NoSuchElementException e) {
//            throw new AuthenticationException("Unauthorized to get ban state");
//        }
//        if (!requestUser.getAsAdmin()) {
//            throw new AuthenticationException("Unauthorized to get ban state");
//        }
//
//        User user = findUserById(id);
//        return user.getDisabled();
//    }
}

