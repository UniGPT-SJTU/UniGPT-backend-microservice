package com.unigpt.chat.dto;

import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.ChatType;

import lombok.Data;

@Data
public class PromptChatDTO {
    private ChatType type;
    private String content;

    public PromptChatDTO(Chat chat) {
        this.type = chat.getType();
        this.content = chat.getContent();
    }

    // Constructor for testing
    public PromptChatDTO(ChatType type, String content) {
        this.type = type;
        this.content = content;
    }
}
