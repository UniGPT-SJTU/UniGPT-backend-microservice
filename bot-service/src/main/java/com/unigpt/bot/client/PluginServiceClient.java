package com.unigpt.bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.unigpt.bot.dto.UpdateBotInfoRequestToPluginServiceDTO;

@FeignClient(name = "plugin-service", url = "${services.plugin-service.url}/internal")
public interface PluginServiceClient {

    @PostMapping("/bots/{botId}")
    void createBot(
            @PathVariable Integer botId,
            @RequestBody UpdateBotInfoRequestToPluginServiceDTO dto);

    @PutMapping("/bots/{botId}")
    void updateBot(
            @PathVariable Integer botId,
            @RequestBody UpdateBotInfoRequestToPluginServiceDTO dto);

}
