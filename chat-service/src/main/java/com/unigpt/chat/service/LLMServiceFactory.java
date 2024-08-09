package com.unigpt.chat.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.unigpt.chat.client.PluginServiceClient;
import com.unigpt.chat.model.BaseModelType;
import com.unigpt.chat.serviceimpl.LLMServiceImpl;

import dev.langchain4j.store.memory.chat.ChatMemoryStore;

@Component
public class LLMServiceFactory {

    private final Map<BaseModelType, LLMService> llmServiceMap;

    public LLMServiceFactory(
            ChatMemoryStore chatMemoryStore,
            // DockerService dockerService,
            KnowledgeService knowledgeService,
            FunGraphService funGraphService,
            PluginServiceClient pluginServiceClient) {
        llmServiceMap = new HashMap<>();
        for (BaseModelType type : BaseModelType.values()) {
            llmServiceMap.put(type, new LLMServiceImpl(
                    type,
                    chatMemoryStore,
                    knowledgeService,
                    funGraphService,
                    pluginServiceClient));
        }
    }

    public LLMService getLLMService(BaseModelType baseModelType) {
        return llmServiceMap.get(baseModelType);
    }
}
