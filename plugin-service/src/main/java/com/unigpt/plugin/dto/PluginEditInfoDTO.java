package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Plugin;
import lombok.Data;

@Data
public class PluginEditInfoDTO {
    private String name;
    private String avatar;

    public PluginEditInfoDTO(Plugin plugin) {
        this.name = plugin.getName();
        this.avatar = plugin.getAvatar();
    }
}
