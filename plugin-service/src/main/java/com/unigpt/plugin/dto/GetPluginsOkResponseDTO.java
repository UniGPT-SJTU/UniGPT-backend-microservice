package com.unigpt.plugin.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetPluginsOkResponseDTO {

    private Integer total;
    private List<PluginBriefInfoDTO> plugins;

    /**
     *
     * @param total 总数
     * @param plugins 插件列表
     */
    public GetPluginsOkResponseDTO(int total, List<PluginBriefInfoDTO> plugins) {
        this.total = total;
        this.plugins = plugins;
    }
}
