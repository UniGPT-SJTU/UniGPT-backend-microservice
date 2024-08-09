package com.unigpt.chat.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unigpt.chat.dto.PluginDTO;

@FeignClient(name = "plugin-service", url = "${plugin-service.url}/internal")
public interface PluginServiceClient {
    // TODO: 设置默认返回值
    @GetMapping("/bots/{botId}/plugins")
    List<PluginDTO> getBotPlugins(@PathVariable Integer botId);
}
