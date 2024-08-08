package com.unigpt.chat.dto;

import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.ChatType;

import lombok.Data;

@Data
public class PromptChatDTO {
    private ChatType type;
    private String content;
    public PromptChatDTO() {
        // not used
    }
    public PromptChatDTO(Chat chat) {
        this.type = chat.getType();
        this.content = chat.getContent();
    }

}
