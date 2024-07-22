package com.unigpt.chat.dto;

import java.util.List;

import com.unigpt.chat.parameter.LLMArgs.LLMArgs;

import lombok.Data;


/**
 * BotHistoryInfoDTO
 * 机器人中用于创建对话历史的必要信息，
 * 需要从Bot微服务获取
 */
@Data
public class BotHistoryInfoDTO {
    private List<PromptChatDTO> promptChats;
    private List<String> promptKeys;
    private LLMArgs llmArgs;
}
