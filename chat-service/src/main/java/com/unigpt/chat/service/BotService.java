package com.unigpt.chat.service;

import java.util.List;

import com.unigpt.chat.dto.BotEditInfoDTO;
import com.unigpt.chat.dto.CreateBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;

public interface BotService {

    /**
     * @brief 创建机器人
     * @param dto   机器人编辑信息
     * @param token 用户token
     * @return 创建结果
     */
    void createBot(Integer userId, Integer id, BotEditInfoDTO dto) throws Exception;

    /**
     * @brief 更新机器人
     * @param id    机器人id
     * @param dto   机器人编辑信息
     * @param token 用户token
     * @return 更新结果
     */
    void updateBot(Integer userId, Boolean isAdmin, Integer id, BotEditInfoDTO dto) throws Exception;

    /**
     * @brief 获取机器人历史记录
     * @param userId   请求用户id
     * @param botId    机器人id
     * @param page     页码
     * @param pageSize 每页大小
     * @return 机器人历史记录
     */
    GetBotHistoryOkResponseDTO getBotHistory(
            Integer userId,
            Integer botId,
            Integer page,
            Integer pageSize);

    /**
     * @brief 添加机器人历史记录
     * @param userId     用户id
     * @param botId      机器人id
     * @param promptList prompt记录内容
     * @return 添加结果
     */
    CreateBotHistoryOkResponseDTO createBotHistory(
            Integer userId,
            Integer botId,
            List<PromptDTO> promptList) throws Exception;
}
