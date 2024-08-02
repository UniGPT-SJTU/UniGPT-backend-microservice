package com.unigpt.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.unigpt.bot.dto.UpdateBotInfoRequestToUserServiceDTO;

@Component
@FeignClient(name = "user-service", url = "${services.user-service.url}/internal")
public interface UserServiceClient {

        @DeleteMapping("/users/used-bots/{botId}")
        void deleteBotFromUsedList(
                        @PathVariable Integer botId,
                        @RequestHeader(name = "X-User-Id") Integer userId);

        @PostMapping("/bots/{botId}")
        void createBot(
                        @PathVariable Integer botId,
                        @RequestBody UpdateBotInfoRequestToUserServiceDTO dto);

        @PutMapping("/bots/{botId}")
        void updateBot(
                        @PathVariable Integer botId,
                        @RequestBody UpdateBotInfoRequestToUserServiceDTO dto);
}
