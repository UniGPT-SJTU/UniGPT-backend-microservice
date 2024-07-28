package com.unigpt.chat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unigpt.chat.dto.BotHistoryInfoDTO;

// @FeignClient(name = "bot-service", url = "${service.bot-service.url}/internal")
@FeignClient(name = "bot-service", url = "http://localhost:8081/internal")
public interface BotServiceClient {
    // @GetMapping("/bots/{botid}/promptchat")
    // List<PromptDTO> getPromptChats(@PathVariable Integer botid);

    @GetMapping("/bots/{botid}?info=history")
    BotHistoryInfoDTO getBotHistoryInfo(@PathVariable Integer botid);
    

}
