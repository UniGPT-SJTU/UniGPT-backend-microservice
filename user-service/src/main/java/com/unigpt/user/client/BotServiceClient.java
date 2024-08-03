package com.unigpt.user.client;

import com.unigpt.user.dto.UserUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "bot-service", url = "${services.bot-service.url}/internal")
public interface BotServiceClient {
    @PostMapping("/users/{id}")
    ResponseEntity<Object> createUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequestDTO dto);
}
