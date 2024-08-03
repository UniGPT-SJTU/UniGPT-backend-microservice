package com.unigpt.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "chat-service", url = "${services.chat-service.url}/internal")
public interface ChatServiceClient {
//    @PostMapping("/bots/{botId}")
//    void createBot(
//            @PathVariable Integer botId,
//            @RequestBody UpdateBotInfoRequestToChatServiceDTO dto);
//
//    @PutMapping("/bots/{botId}")
//    void updateBot(
//            @PathVariable Integer botId,
//            @RequestBody UpdateBotInfoRequestToChatServiceDTO dto);
}

