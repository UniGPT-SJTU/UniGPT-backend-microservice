package com.unigpt.plugin.service;

import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.BotPluginInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;

import java.util.List;

public interface BotService {
    ResponseDTO createBot(BotInfoDTO dto, Integer botid);

    ResponseDTO updateBot(BotInfoDTO dto, Integer botid);

    List<BotPluginInfoDTO> getPlugins(Integer botid);
}
