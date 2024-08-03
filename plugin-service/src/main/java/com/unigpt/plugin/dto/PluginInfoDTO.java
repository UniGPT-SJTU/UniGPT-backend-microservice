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
}
