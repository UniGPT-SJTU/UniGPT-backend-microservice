package com.unigpt.chat.service;

import javax.naming.AuthenticationException;

import com.unigpt.chat.dto.UpdateUserInfoRequestDTO;

public interface UserService {

    /**
     * @brief 创建用户
     * @param id                    用户id
     * @param updateUserInfoRequestDTO 创建用户信息请求
     */
    void createUser(Integer id, UpdateUserInfoRequestDTO updateUserInfoRequestDTO);


    /**
     * @brief 更新用户信息
     * @param targetUserId             目标用户id
     * @param updateUserInfoRequestDTO 更新用户信息请求
     */
    void updateUserInfo(
            Integer targetUserId,
            UpdateUserInfoRequestDTO updateUserInfoRequestDTO) throws AuthenticationException;
}
