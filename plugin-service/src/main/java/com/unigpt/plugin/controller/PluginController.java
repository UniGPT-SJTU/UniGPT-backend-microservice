package com.unigpt.plugin.controller;

import com.unigpt.plugin.service.PluginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/internal/plugin")
public class PluginController {
    PluginService pluginService;

    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Object> createPlugin(@RequestBody PluginCreateDTO dto, @CookieValue("token") String token) {
//        try {
//            return ResponseEntity.ok(pluginService.createPlugin(dto, token));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseDTO(false, e.getMessage()));
//        }
//    }
}