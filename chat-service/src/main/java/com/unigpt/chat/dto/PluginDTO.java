package com.unigpt.chat.dto;

import java.util.List;

import lombok.Data;

@Data
public class PluginDTO {
    private String name;
    private String creator;
    private String urn;
    private String description;
    private List<ParameterDTO> parameters;
}
