package com.unigpt.plugin.dtoFactory;

import com.unigpt.plugin.dto.BotInfoDTO;

import java.util.List;

public class dtoFactory {
    public static BotInfoDTO createBotInfoDTO(Integer seed) {
        return new BotInfoDTO("name" + seed.toString(),
                "avatar" + seed.toString(),
                "description" + seed.toString(),
                List.of(seed, seed + 10));
    }


}
