package com.unigpt.bot.dto;

import lombok.Data;

@Data
public class UpdateBotInfoRequestToUserServiceDTO {
    private String name;
    private String avatar;
    private String description;

    public UpdateBotInfoRequestToUserServiceDTO(String name, String avatar, String description) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
    }
}
