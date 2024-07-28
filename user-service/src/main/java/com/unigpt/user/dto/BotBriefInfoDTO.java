package com.unigpt.user.dto;

import com.unigpt.user.model.Bot;
import com.unigpt.user.model.User;
import lombok.Data;

@Data
public class BotBriefInfoDTO {

    private Integer id;
    private String name;
    private String description;
    private String avatar;
    private boolean asCreator;

    public BotBriefInfoDTO(Integer id, String name, String description, String avatar, boolean asCreator,
                           boolean asAdmin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.asCreator = asCreator;
    }

    public BotBriefInfoDTO(Bot bot, User user) {
        this.id = bot.getId();
        this.name = bot.getName();
        this.description = bot.getDescription();
        this.avatar = bot.getAvatar();
        this.asCreator = bot.getCreator().equals(user);
    }
}
