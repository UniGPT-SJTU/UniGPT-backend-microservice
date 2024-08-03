package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.model.User;
import lombok.Data;

@Data
public class PluginBriefInfoDTO {

    private Integer id;
    private String name;
    private String description;
    private String avatar;

    public PluginBriefInfoDTO(Integer id, String name, String description, String avatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
    }

    public PluginBriefInfoDTO(Plugin plugin, User user) {
        this.id = plugin.getId();
        this.name = plugin.getName();
        this.description = plugin.getDescription();
        this.avatar = plugin.getAvatar();
    }

    public PluginBriefInfoDTO() {
        // only for test
    }
//
//    public PluginBriefInfoDTO(Plugin plugin) {
//        this.id = plugin.getId();
//        this.name = plugin.getName();
//        this.description = plugin.getDescription();
//        this.avatar = plugin.getAvatar();
//    }
}
