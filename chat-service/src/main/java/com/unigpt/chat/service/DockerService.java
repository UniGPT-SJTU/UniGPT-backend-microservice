package com.unigpt.chat.service;

import java.util.List;

public interface DockerService {

    /**
     * @brief 调用python函数（目前为Docker容器实现）
     * @param moduleName 模块名称
     * @param functionName 函数名
     * @param params 参数列表
     * @return
     */
    String invokeFunction(String username, String moduleName, String functionName, List<String> params);
}
