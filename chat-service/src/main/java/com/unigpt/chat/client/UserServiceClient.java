package com.unigpt.chat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8082/internal")
public interface UserServiceClient {
    
    @PutMapping("/users/used-bots/{botId}")
    public void updateUsedBots(
            @RequestHeader(name = "X-User-Id") Integer userId,
            @RequestParam Integer botId);
}
