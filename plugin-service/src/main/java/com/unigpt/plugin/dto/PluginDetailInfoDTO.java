package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.model.User;
import lombok.Data;

import java.util.List;

@Data
public class PluginDetailInfoDTO {

    private Integer id;
    private String name;
    private String creator;
    private Integer creatorId;
    private String description;
    private List<String> photos;
    private String detail;
    private String avatar;
    private boolean asCreator;
    private List<BotBriefInfoDTO> bots;

    public PluginDetailInfoDTO() {
        // only for test
    }

    public PluginDetailInfoDTO(Plugin plugin, User user) {
        this.id = plugin.getId();
        this.name = plugin.getName();
        this.creator = plugin.getCreator().getName();
        this.creatorId = plugin.getCreator().getTrueId();
        this.description = plugin.getDescription();
        this.photos = plugin.getPhotos();
        this.detail = plugin.getDetail();
        this.avatar = plugin.getAvatar();
        this.asCreator = plugin.getCreator().equals(user);
        this.bots = plugin.getBots().stream().map(BotBriefInfoDTO::new).toList();
    }
}
