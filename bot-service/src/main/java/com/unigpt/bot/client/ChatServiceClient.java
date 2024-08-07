package com.unigpt.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.unigpt.bot.dto.UpdateBotInfoRequestToChatServiceDTO;

@FeignClient(name = "chat-service", url = "${services.chat-service.url}/internal")
public interface ChatServiceClient {
        @PostMapping("/bots/{botId}")
        void createBot(
                        @PathVariable Integer botId,
                        @RequestBody UpdateBotInfoRequestToChatServiceDTO dto,
                        @RequestHeader(name = "X-User-Id") Integer userId);

        @PutMapping("/bots/{botId}")
        void updateBot(
                        @PathVariable Integer botId,
                        @RequestBody UpdateBotInfoRequestToChatServiceDTO dto,
                        @RequestHeader(name = "X-User-Id") Integer userId,
                        @RequestHeader(name = "X-Is-Admin") Boolean isAdmin);

}
