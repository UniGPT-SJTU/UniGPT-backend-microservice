package com.unigpt.plugin.service;

import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;

public interface BotService {
    ResponseDTO createBot(BotInfoDTO dto, Integer botid);

    ResponseDTO updateBot(BotInfoDTO dto, Integer botid);
}
