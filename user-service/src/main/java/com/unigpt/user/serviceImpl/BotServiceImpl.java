package com.unigpt.user.serviceImpl;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.model.Bot;
import com.unigpt.user.model.User;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.repository.UserRepository;
import com.unigpt.user.service.BotService;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import java.util.NoSuchElementException;

@Service
public class BotServiceImpl implements BotService{
    private final BotRepository botRepository;
    private final UserRepository userRepository;
    public BotServiceImpl(BotRepository botRepository, UserRepository userRepository) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO createBot( BotEditInfoDTO dto, Integer botid, Integer userid) throws Exception{
        // check if userid is valid
        User user = userRepository.findById(userid).orElseThrow(() -> new Exception("User not found"));
        // check if botid is already in use
        if(botRepository.existsById(botid)){
            throw new Exception("Bot id already in use");
        }
        Bot bot = new Bot(dto, user);
        bot.setTrueId(botid);
        botRepository.save(bot);

        // add bot to user's created_bots list
        user.getCreateBots().add(bot);
        userRepository.save(user);

        return new ResponseDTO(true, String.valueOf(botid));
    }


    public ResponseDTO updateBot(Integer botid, BotEditInfoDTO dto, Integer userid){
        Bot bot = botRepository.findByTrueId(botid).orElseThrow(() -> new NoSuchElementException("Bot not found"));
        bot.setName(dto.getName());
        bot.setAvatar(dto.getAvatar());
        bot.setDescription(dto.getDescription());
        botRepository.save(bot);
        return new ResponseDTO(true, "Bot updated successfully");
    }

}