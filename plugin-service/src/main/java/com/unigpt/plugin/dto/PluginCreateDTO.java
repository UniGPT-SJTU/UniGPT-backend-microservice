package com.unigpt.plugin.dto;

import java.util.List;

import lombok.Data;

@Data
public class PluginCreateDTO {

    String name;
    String description;
    String avatar;
    List<String> photos;
    List<ParameterDTO> parameters;
    String code;
    String detail;
    Boolean isPublished;
}
