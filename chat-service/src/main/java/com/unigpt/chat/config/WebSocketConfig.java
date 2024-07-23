package com.unigpt.chat.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.unigpt.chat.service.ChatHistoryService;
import com.unigpt.chat.service.LLMServiceFactory;
import com.unigpt.chat.websocket.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHistoryService chatHistoryService;
    private final LLMServiceFactory llmServiceFactory;

    public WebSocketConfig(
        ChatHistoryService chatHistoryService,
        LLMServiceFactory llmServiceFactory
    ) {
        this.chatHistoryService = chatHistoryService;
        this.llmServiceFactory = llmServiceFactory;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(
            new ChatWebSocketHandler(
                chatHistoryService, 
                llmServiceFactory
            ), 
            "/chat"
        )
                .setAllowedOrigins("*");
    }
}