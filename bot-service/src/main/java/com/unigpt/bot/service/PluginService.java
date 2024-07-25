package com.unigpt.bot.service;

import com.unigpt.bot.dto.PluginEditInfoDTO;
import com.unigpt.bot.dto.ResponseDTO;

public interface PluginService {
    /**
     * @brief 创建插件
     * @param pluginId 已经生成的插件id
     * @param dto 插件编辑信息
     * @return
     */
    ResponseDTO createPlugin(Integer pluginId, PluginEditInfoDTO dto);
}
