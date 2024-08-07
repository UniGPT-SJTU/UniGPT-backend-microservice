package com.unigpt.bot.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unigpt.bot.dto.UpdateBotInfoRequestToPluginServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void updateBotToPluginService(Integer botId, UpdateBotInfoRequestToPluginServiceDTO dto) {
        try {
            String dtoAsString = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("update_bot", botId, dtoAsString);
            System.out.println("Produced message: " + dtoAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}