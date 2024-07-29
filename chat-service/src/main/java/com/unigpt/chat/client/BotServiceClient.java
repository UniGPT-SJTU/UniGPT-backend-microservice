package com.unigpt.chat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unigpt.chat.dto.BotHistoryInfoDTO;

@FeignClient(name = "bot-service", url = "${bot-service.url}/internal")
public interface BotServiceClient {

    @GetMapping("/bots/{botid}?info=history")
    BotHistoryInfoDTO getBotHistoryInfo(@PathVariable Integer botid);
}
