package com.unigpt.bot.model;

import com.unigpt.bot.dto.PromptChatDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prompt_chat")
public class PromptChat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "type")
    private ChatType type;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    public PromptChat(ChatType type, String content) {
        this.type = type;
        this.content = content;
    }

    public PromptChat(PromptChatDTO dto) {
        this.type = dto.getType();
        this.content = dto.getContent();
    }

    public PromptChat() {
        // not used
    }
}
