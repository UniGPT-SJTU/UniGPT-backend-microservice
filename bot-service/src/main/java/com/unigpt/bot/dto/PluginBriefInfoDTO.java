package com.unigpt.bot.dto;

import com.unigpt.bot.model.Plugin;

import lombok.Data;

@Data
public class PluginBriefInfoDTO {

    private Integer id;
    private String name;
    private String avatar;

    public PluginBriefInfoDTO(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public PluginBriefInfoDTO(Plugin plugin) {
        this.id = plugin.getId();
        this.name = plugin.getName();
        this.avatar = plugin.getAvatar();
    }

    public PluginBriefInfoDTO() {
        // not used
    }

}
