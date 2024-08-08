package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Parameter;
import lombok.Data;

@Data
public class ParameterDTO {
    String name;
    String type;
    String description;

    public ParameterDTO(Parameter parameter){
        this.name = parameter.getName();
        this.type = parameter.getType();
        this.description = parameter.getDescription();
    }
}
