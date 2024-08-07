package com.unigpt.plugin.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBotInfoRequestToPluginServiceDTO {
    private String name;
    private String avatar;
    private String description;
    private List<Integer> plugin_ids;

    public UpdateBotInfoRequestToPluginServiceDTO(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
        this.description = "";
        this.plugin_ids = List.of();
    }
}
