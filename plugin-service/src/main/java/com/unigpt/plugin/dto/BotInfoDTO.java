package com.unigpt.plugin.dto;


import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class BotInfoDTO {

    private String name;
    private String avatar;
    private List<Integer> plugin_ids;

    public BotInfoDTO() {
        // not used
    }
}
