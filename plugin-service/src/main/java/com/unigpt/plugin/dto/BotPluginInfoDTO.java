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
        this.name = plugin.getName();
        this.creator = plugin.getCreator().getAccount();
        this.description = plugin.getDescription();
        this.urn = plugin.getUrn();
        this.parameters = plugin.getParameters().stream()
                .map(ParameterDTO::new)
                .toList();
    }
}
