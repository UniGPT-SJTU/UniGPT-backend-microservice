package com.unigpt.user.serviceImpl;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.model.Bot;
import com.unigpt.user.model.User;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.repository.UserRepository;
import com.unigpt.user.service.BotService;
import org.springframework.stereotype.Service;

@Service
public class BotServiceImpl implements BotService{
    private final BotRepository botRepository;
    private final UserRepository userRepository;
    public BotServiceImpl(BotRepository botRepository, UserRepository userRepository) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO createBot(BotEditInfoDTO dto, Integer botid, Integer userid) throws Exception{
        // check if userid is valid
        User user = userRepository.findById(userid).orElseThrow(() -> new Exception("User not found"));
        // check if botid is already in use
        if(botRepository.existsById(botid)){
            throw new Exception("Bot id already in use");
        }
        Bot bot = new Bot(dto, user);
        botRepository.save(bot);

        // add bot to user's created_bots list
        user.getCreateBots().add(bot);
        userRepository.save(user);

        return new ResponseDTO(true, String.valueOf(botid));
    }

}