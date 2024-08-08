package com.unigpt.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "plugin-service", url = "${services.plugin-service.url}/internal")
public interface PluginServiceClient {
    @PostMapping("/users/{userid}")
    ResponseEntity<Object> createUser(
            @PathVariable Integer userid,
            @RequestParam String name);
}