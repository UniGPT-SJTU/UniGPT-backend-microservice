package com.unigpt.bot.service;

import javax.naming.AuthenticationException;

import com.unigpt.bot.dto.GetBotsOkResponseDTO;
import com.unigpt.bot.dto.UpdateUserInfoRequestDTO;

public interface UserService {

    /**
     * @brief 创建用户
     * @param id                    用户id
     * @param updateUserInfoRequestDTO 创建用户信息请求
     */
    void createUser(Integer id, UpdateUserInfoRequestDTO updateUserInfoRequestDTO);


    /**
     * @brief 更新用户信息
     * @param requestUserId            请求用户id
     * @param targetUserId             目标用户id
     * @param updateUserInfoRequestDTO 更新用户信息请求
     */
    void updateUserInfo(
            Integer requestUserId,
            Integer targetUserId,
            UpdateUserInfoRequestDTO updateUserInfoRequestDTO) throws AuthenticationException;

    /**
     * @brief 获取收藏的机器人
     * @param userId   用户id
     * @param page     页码
     * @param pageSize 每页大小
     */
    GetBotsOkResponseDTO getStarredBots(Integer userId, Integer page, Integer pageSize);

    /**
     * @brief 获取创建的机器人
     * @param userId   用户id
     * @param page     页码
     * @param pageSize 每页大小
     */
    GetBotsOkResponseDTO getCreatedBots(Integer userId, Integer page, Integer pageSize);
}
