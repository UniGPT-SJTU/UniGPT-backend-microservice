package com.unigpt.plugin.dto;


import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class BotInfoDTO {

    private String name;
    private String avatar;
    private String description;
    private List<Integer> plugin_ids;

    public BotInfoDTO() {
        // not used
    }

    public BotInfoDTO(String name, String avatar, String description, List<Integer> plugin_ids) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.plugin_ids = plugin_ids;
    }
}
