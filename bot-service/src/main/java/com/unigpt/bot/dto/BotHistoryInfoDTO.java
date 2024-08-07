package com.unigpt.bot.dto;

import java.util.List;

import com.unigpt.bot.LLMArgs.LLMArgs;
import com.unigpt.bot.model.Bot;

import lombok.Data;

@Data
public class BotHistoryInfoDTO {
    List<PromptChatDTO> promptChats;
    List<String> promptKeys;
    LLMArgs llmArgs;

    public BotHistoryInfoDTO(Bot bot) {
        this.promptChats = bot.getPromptChats().stream().map(PromptChatDTO::new).toList();
        this.promptKeys = bot.getPromptKeys();
        this.llmArgs = bot.getLlmArgs();
    }
}
