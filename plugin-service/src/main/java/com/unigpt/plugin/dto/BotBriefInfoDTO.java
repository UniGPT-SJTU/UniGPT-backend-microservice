package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Bot;
import lombok.Data;

@Data
public class BotBriefInfoDTO {
    private Integer id;
    private String name;
    private String avatar;
    private String description;

    public BotBriefInfoDTO(Bot bot) {
        this.id = bot.getTrueId();
        this.name = bot.getName();
        this.avatar = bot.getAvatar();
        this.description = bot.getDescription();
    }
}
