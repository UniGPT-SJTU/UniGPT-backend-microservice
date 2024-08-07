package com.unigpt.bot.dto;

import java.util.List;

import lombok.Data;

@Data
public class UpdateBotInfoRequestToPluginServiceDTO {
    private String name;
    private String avatar;
    private String description;
    private List<Integer> plugin_ids;

    public UpdateBotInfoRequestToPluginServiceDTO(String name, String avatar, String description,
            List<Integer> plugin_ids) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.plugin_ids = plugin_ids;
    }
}
