package com.unigpt.plugin.dto;

import lombok.Data;

import java.util.List;

@Data
public class PluginInfoDTO {
    String name;
    String avatar;
    String description;
    String detail;
    List<String> photos;
    List<ParameterDTO> parameters;
    String code;
    Boolean isPublished;

    public PluginInfoDTO(
            String name,
            String avatar,
            String description,
            String detail,
            List<String> photos,
            List<ParameterDTO> parameters,
            String code,
            Boolean isPublished) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.detail = detail;
        this.photos = photos;
        this.parameters = parameters;
        this.code = code;
        this.isPublished = isPublished;
    }

    public PluginInfoDTO() {
        // only for test
    }
}
