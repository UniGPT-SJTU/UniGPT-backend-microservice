package com.unigpt.user.serviceImpl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.unigpt.user.dto.BotEditInfoDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.model.Bot;
import com.unigpt.user.repository.BotRepository;
import com.unigpt.user.service.BotService;

@Service
public class BotServiceImpl implements BotService{
    private final BotRepository botRepository;
    public BotServiceImpl(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    public ResponseDTO createBot( BotEditInfoDTO dto, Integer botid) throws Exception{
        // check if botid is already in use
        if(botRepository.existsById(botid)){
            throw new Exception("Bot id already in use");
        }
        Bot bot = new Bot(dto);
        bot.setTrueId(botid);
        botRepository.save(bot);

        return new ResponseDTO(true, String.valueOf(botid));
    }


    public ResponseDTO updateBot(Integer botid, BotEditInfoDTO dto){
        Bot bot = botRepository.findByTrueId(botid).orElseThrow(() -> new NoSuchElementException("Bot not found"));
        bot.setName(dto.getName());
        bot.setAvatar(dto.getAvatar());
        bot.setDescription(dto.getDescription());
        botRepository.save(bot);
        return new ResponseDTO(true, "Bot updated successfully");
    }

}