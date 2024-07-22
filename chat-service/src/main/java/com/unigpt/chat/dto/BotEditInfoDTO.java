package com.unigpt.chat.dto;

import lombok.Data;

@Data
public class BotEditInfoDTO {
    private String name;
    private String avatar;
    private String description;
    private Boolean isPublished;
}
