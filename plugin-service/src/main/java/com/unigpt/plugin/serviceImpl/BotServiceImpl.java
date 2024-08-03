package com.unigpt.plugin.serviceImpl;

import com.unigpt.plugin.Repository.BotRepository;
import com.unigpt.plugin.Repository.PluginRepository;
import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.model.Bot;
import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.service.BotService;
import com.unigpt.plugin.service.PluginService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BotServiceImpl implements BotService {
    BotRepository botRepository;
    PluginRepository pluginRepository;

    public BotServiceImpl(BotRepository botRepository, PluginRepository pluginRepository){
        this.botRepository = botRepository;
        this.pluginRepository = pluginRepository;
    }

    public ResponseDTO createBot(BotInfoDTO dto, Integer botid){
        // 创建bot的plugin列表
        // 对于每个plugin, 获取其中的id,使用findById方法获取plugin对象, 如果不存在则抛出异常
        // 如果存在则将plugin对象加入到plugin列表中
        List<Plugin> plugins = dto.getPlugin_ids().stream()
                .map(pluginId -> pluginRepository.findById(pluginId)
                        .orElseThrow(() -> new NoSuchElementException("Plugin not found for ID: " + pluginId)))
                .toList();

        Bot newBot = new Bot(dto, botid);
        newBot.setPlugins(plugins);
        botRepository.save(newBot);

        return new ResponseDTO(true, "Bot created successfully");
    }
}
