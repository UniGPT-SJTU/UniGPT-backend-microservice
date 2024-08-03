package com.unigpt.plugin.client;

import com.unigpt.plugin.dto.PluginEditInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bot-service", url = "${services.bot-service.url}/internal")
public interface BotServiceClient {
    @PostMapping("/plugin/{pluginId}")
    ResponseEntity<Object> createPlugin(
            @PathVariable Integer pluginId,
            @RequestBody PluginEditInfoDTO pluginEditInfoDTO
    );
}
