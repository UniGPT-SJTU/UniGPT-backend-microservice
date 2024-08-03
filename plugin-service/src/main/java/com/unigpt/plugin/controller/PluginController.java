package com.unigpt.plugin.controller;

import com.unigpt.plugin.dto.PluginInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.service.PluginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/internal/plugin")
public class PluginController {
    PluginService pluginService;

    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @PostMapping
    public ResponseEntity<Object> createPlugin(
            @RequestBody PluginInfoDTO dto,
            @RequestHeader("X-User-Id") Integer userid){
        try {
            return ResponseEntity.ok(pluginService.createPlugin(dto, userid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}