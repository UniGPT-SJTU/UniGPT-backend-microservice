package com.unigpt.user.dto;

import com.unigpt.user.model.Bot;

import lombok.Data;

@Data
public class BotBriefInfoDTO {

    private Integer id;
    private String name;
    private String description;
    private String avatar;

    public BotBriefInfoDTO(Integer id, String name, String description, String avatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
    }

    public BotBriefInfoDTO(Bot bot) {
        this.id = bot.getTrueId();
        this.name = bot.getName();
        this.description = bot.getDescription();
        this.avatar = bot.getAvatar();
    }
}
