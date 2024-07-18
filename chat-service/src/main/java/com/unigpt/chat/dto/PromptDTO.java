package com.unigpt.chat.dto;

import lombok.Data;

@Data
public class PromptDTO {

    private final String promptKey;
    private final String promptValue;

    public PromptDTO(String promptKey, String promptValue) {
        this.promptKey = promptKey;
        this.promptValue = promptValue;
    }
}
