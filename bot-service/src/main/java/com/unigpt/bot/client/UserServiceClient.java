package com.unigpt.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "user-service", url = "${services.user-service.url}/internal")
public interface UserServiceClient {

    @DeleteMapping("/users/used-bots/{botId}")
    public void deleteBotFromUsedList(
            @PathVariable Integer botId,
            @RequestHeader(name = "X-User-Id") Integer userId);
}
