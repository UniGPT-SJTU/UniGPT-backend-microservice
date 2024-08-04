package com.unigpt.plugin.dtoFactory;

import com.unigpt.plugin.dto.*;

import java.util.ArrayList;
import java.util.List;

public class dtoFactory {
    public static BotInfoDTO createBotInfoDTO(Integer seed) {
        return new BotInfoDTO(
                "name" + seed.toString(),
                "avatar" + seed.toString(),
                "description" + seed.toString(),
                List.of(seed, seed + 10));
    }

    public static BotBriefInfoDTO createBotBriefInfoDTO(Integer seed) {
        return new BotBriefInfoDTO(
                seed,
                "name" + seed.toString(),
                "avatar" + seed.toString(),
                "description" + seed.toString());
    }

    public static ParameterDTO createParameterDTO(Integer seed) {
        return new ParameterDTO(
                "name" + seed.toString(),
                "type" + seed.toString(),
                "description" + seed.toString());
    }

    public static PluginInfoDTO createPluginInfoDTO(Integer seed) {
        Integer seed2 = seed + 10;
        return new PluginInfoDTO(
                "name" + seed.toString(),
                "avatar" + seed.toString(),
                "description" + seed.toString(),
                "detail" + seed.toString(),
                List.of("photo" + seed.toString(), "photo" + seed2.toString()),
                List.of(createParameterDTO(seed), createParameterDTO(seed2)),
                "code" + seed.toString(),
                true);
    }

    public static PluginBriefInfoDTO createPluginBriefInfoDTO(Integer seed) {
        return new PluginBriefInfoDTO(
                seed,
                "name" + seed.toString(),
                "avatar" + seed.toString(),
                "description" + seed.toString());
    }

    public static PluginDetailInfoDTO createPluginDetailInfoDTO(Integer seed) {
        int seed2 = seed + 10;
        return new PluginDetailInfoDTO(
                seed,
                "name" + seed.toString(),
                "creator" + seed.toString(),
                seed,
                "description" + seed.toString(),
                List.of("photo" + seed.toString(), "photo" + Integer.toString(seed2)),
                "detail" + seed.toString(),
                "avatar" + seed.toString(),
                true,
                List.of(createBotBriefInfoDTO(seed), createBotBriefInfoDTO(seed + 10)));
    }

    public static GetPluginsOkResponseDTO createGetPluginsOkResponseDTO(Integer seed) {
        List<PluginBriefInfoDTO> plugins = new ArrayList<>();
        for (int i = 0; i < seed; i++) {
            plugins.add(createPluginBriefInfoDTO(i));
        }
        return new GetPluginsOkResponseDTO(seed, plugins);
    }

}
