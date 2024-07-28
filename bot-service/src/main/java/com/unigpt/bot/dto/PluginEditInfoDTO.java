package com.unigpt.bot.dto;

import lombok.Data;

@Data
public class PluginEditInfoDTO {
    private String name;
    private String avatar;

    public PluginEditInfoDTO(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
