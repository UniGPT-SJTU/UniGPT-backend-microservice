package com.unigpt.bot.serviceimpl;

import org.springframework.stereotype.Service;

import com.unigpt.bot.dto.PluginEditInfoDTO;
import com.unigpt.bot.dto.ResponseDTO;
import com.unigpt.bot.model.Plugin;
import com.unigpt.bot.repository.PluginRepository;
import com.unigpt.bot.service.PluginService;

@Service
public class PluginServiceImpl implements PluginService {
    private final PluginRepository pluginRepository;

    public PluginServiceImpl(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    @Override
    public ResponseDTO createPlugin(Integer pluginId, PluginEditInfoDTO dto) {
        Plugin plugin = new Plugin(pluginId, dto.getName(), dto.getAvatar());
        pluginRepository.save(plugin);
        return new ResponseDTO(true, "plugin created successfully");
    }
}
