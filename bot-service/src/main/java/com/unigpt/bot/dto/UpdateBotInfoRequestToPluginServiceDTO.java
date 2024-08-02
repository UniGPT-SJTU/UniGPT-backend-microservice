package com.unigpt.bot.dto;

import lombok.Data;

@Data
public class UpdateBotInfoRequestToPluginServiceDTO {
    private String name;
    private String avatar;

    public UpdateBotInfoRequestToPluginServiceDTO(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
