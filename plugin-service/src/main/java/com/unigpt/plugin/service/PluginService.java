package com.unigpt.plugin.service;


import com.unigpt.plugin.dto.GetPluginsOkResponseDTO;
import com.unigpt.plugin.dto.PluginDetailInfoDTO;
import com.unigpt.plugin.dto.PluginInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;

public interface PluginService {
    /**
     * @brief 创建插件
     * @param dto 插件编辑信息
     * @param userid 用户id
     * @return 创建结果
     */
    ResponseDTO createPlugin(PluginInfoDTO dto, Integer userid) throws Exception;


    /**
     * @brief 获取插件列表
     * @param q 搜索关键字
     * @param order 排序方式
     * @param page 页码
     * @param pageSize 每页大小
     * @return 插件列表
     */
    GetPluginsOkResponseDTO getPlugins(String q, String order, Integer page, Integer pageSize);

    PluginDetailInfoDTO getPluginInfo(Integer pluginid, Integer userid);
}
