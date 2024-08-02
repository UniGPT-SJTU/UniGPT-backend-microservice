package com.unigpt.bot.dto;

import lombok.Data;

@Data
public class UpdateBotInfoRequestToChatServiceDTO {
    private String name;
    private String avatar;
    private String description;
    private Boolean isPublished;

    public UpdateBotInfoRequestToChatServiceDTO(String name, String avatar, String description, Boolean isPublished) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.isPublished = isPublished;
    }
}
