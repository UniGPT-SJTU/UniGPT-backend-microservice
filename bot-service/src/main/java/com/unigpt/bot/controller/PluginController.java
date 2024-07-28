package com.unigpt.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unigpt.bot.dto.PluginEditInfoDTO;
import com.unigpt.bot.service.PluginService;

@RestController
@RequestMapping("/internal/plugin")
public class PluginController {
    private final PluginService pluginService;

    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @PostMapping("/{pluginId}")
    public ResponseEntity<Object> createPlugin(
            @PathVariable Integer pluginId,
            @RequestBody PluginEditInfoDTO pluginEditInfoDTO) {
        try {
            return ResponseEntity.ok(pluginService.createPlugin(pluginId, pluginEditInfoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
