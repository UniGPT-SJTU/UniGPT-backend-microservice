package com.unigpt.plugin.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.dto.UpdateBotInfoRequestToPluginServiceDTO;
import com.unigpt.plugin.service.BotService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class KafkaConsumer {

    @Autowired
    BotService botService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "update_bot", groupId = "my-group")
    public ResponseEntity<ResponseDTO> updateBot(ConsumerRecord<Integer, String> record) {
        try {
            Integer botId = record.key();
            String dtoAsString = record.value();
            UpdateBotInfoRequestToPluginServiceDTO dto = objectMapper.readValue(dtoAsString, UpdateBotInfoRequestToPluginServiceDTO.class);
            BotInfoDTO botInfoDTO = new BotInfoDTO(dto.getName(), dto.getAvatar(), dto.getDescription(), dto.getPlugin_ids());
            System.out.println("Consumed message: " + dto);
            return ResponseEntity.ok(botService.updateBot(botInfoDTO, botId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }
}