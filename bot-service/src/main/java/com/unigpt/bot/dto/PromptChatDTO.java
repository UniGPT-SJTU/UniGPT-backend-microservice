package com.unigpt.bot.dto;

import com.unigpt.bot.model.ChatType;
import com.unigpt.bot.model.PromptChat;

import lombok.Data;

@Data
public class PromptChatDTO {
    private ChatType type;
    private String content;

    public PromptChatDTO(PromptChat promptChat) {
        this.type = promptChat.getType();
        this.content = promptChat.getContent();
    }

    public PromptChatDTO() {
        // not used
    }

}
