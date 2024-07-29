package com.unigpt.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "chat-service", url = "${services.chat-service.url}/internal")
public interface ChatServiceClient {
    @PostMapping("/bots")
    void createBot()
}
