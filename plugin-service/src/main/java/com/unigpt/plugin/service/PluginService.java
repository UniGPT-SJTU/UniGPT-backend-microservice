package com.unigpt.plugin.service;

import com.unigpt.plugin.dto.GetPluginsOkResponseDTO;
import com.unigpt.plugin.dto.PluginCreateDTO;
import com.unigpt.plugin.dto.PluginCreateTestDTO;
import com.unigpt.plugin.dto.PluginDetailInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;

public interface PluginService {

    /**
     * @brief 创建插件
     * @param dto 插件编辑信息
     * @param userid 用户id
     * @return 创建结果
     */
    ResponseDTO createPlugin(PluginCreateDTO dto, Integer userid) throws Exception;

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

    ResponseDTO testCreatePlugin(PluginCreateTestDTO dto, Integer userid) throws Exception;

//    /**
//     * @brief 获取插件详细信息
//     * @param id 插件id
//     * @param token 用户token
//     * @return 插件详细信息
//     */
//    PluginDetailInfoDTO getPluginInfo(Integer id, String token);
//
//    /**
//     * @brief 获取插件编辑信息
//     * @param id 插件id
//     * @param token 用户token
//     * @return 插件编辑信息
//     */
//    PluginEditInfoDTO getPluginEditInfo(Integer id, String token);
//
//    /**
//     * @brief 测试插件
//     * @param dto 插件编辑信息
//     * @param token 用户token
//     * @param params 参数列表
//     * @return 创建结果
//     */
//    ResponseDTO testCreatePlugin(PluginCreateTestDTO dto, String token) throws Exception;
}
