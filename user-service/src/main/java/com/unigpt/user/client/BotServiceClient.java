package com.unigpt.user.client;

import com.unigpt.user.dto.UserUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "bot-service", url = "${services.bot-service.url}/internal")
public interface BotServiceClient {
    @PostMapping("/users/{id}")
    ResponseEntity<Object> createUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequestDTO dto);

    @PutMapping("/users/{id}")
    ResponseEntity<Object> updateUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequestDTO updateUserInfoRequestDTO);
}
