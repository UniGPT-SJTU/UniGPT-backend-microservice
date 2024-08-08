package com.unigpt.plugin.dto;

import lombok.Data;

import java.util.List;

@Data
public class PluginCreateTestDTO {

    String name;
    String description;
    String avatar;
    List<String> photos;
    List<ParameterDTO> parameters;
    String code;
    String detail;
    Boolean isPublished;
    List<String> paramsValue;
}