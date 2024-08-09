package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Parameter;

import lombok.Data;

@Data
public class ParameterDTO {

    String name;
    String type;
    String description;

    public ParameterDTO(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public ParameterDTO() {
        // only for test
    }

    public ParameterDTO(Parameter parameter) {
        this.name = parameter.getName();
        this.type = parameter.getType();
        this.description = parameter.getDescription();
    }
}
