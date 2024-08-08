package com.unigpt.plugin.dto;

import com.unigpt.plugin.model.Plugin;
import lombok.Data;

import java.util.List;

@Data
public class BotPluginInfoDTO {
    String name;
    String creator;
    String description;
    String urn;
    List<ParameterDTO> parameters;

    public BotPluginInfoDTO(Plugin plugin){
//        TODO
    }
}
