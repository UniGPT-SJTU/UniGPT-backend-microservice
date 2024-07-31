package com.unigpt.user.service;


import com.unigpt.user.dto.GetBotsOkResponseDTO;
import com.unigpt.user.dto.GetUsersOkResponseDTO;
import com.unigpt.user.dto.ResponseDTO;
import com.unigpt.user.dto.UserUpdateDTO;
import com.unigpt.user.model.User;

import javax.security.sasl.AuthenticationException;

public interface UserService {

    Integer createUser(String email, String account, String name);
    User findUserById(Integer id);
    void updateUserInfo(Integer id, UserUpdateDTO userUpdateDTO);
    GetUsersOkResponseDTO getUsers(Integer page, Integer pagesize, String type, String q);

    /**
     * @brief 获取使用过的机器人
     * @param userid   用户id
     * @param page     页码
     * @param pageSize 每页大小
     */
    GetBotsOkResponseDTO getUsedBots(Integer userid, Integer page, Integer pageSize);

    ResponseDTO useBot(Integer botId, Integer userId);
//
//    /**
//     * @brief 获取收藏的机器人
//     * @param userid   用户id
//     * @param token    用户token
//     * @param page     页码
//     * @param pageSize 每页大小
//     */
//    GetBotsOkResponseDTO getStarredBots(Integer userid, String token, Integer page, Integer pageSize)
//            throws AuthenticationException;
//
//    /**
//     * @brief 获取创建的机器人
//     * @param userid   用户id
//     * @param token    用户token
//     * @param page     页码
//     * @param pageSize 每页大小
//     */
//    GetBotsOkResponseDTO getCreatedBots(Integer userid, String token, Integer page, Integer pageSize);

//
//    /**
//     * @brief 禁用/解除禁用用户
//     * @param id    用户id
//     * @param token 用户token
//     * @param state 状态
//     */
//    void setBanUser(Integer id, String token, Boolean state) throws AuthenticationException;
//
//    /**
//     * @brief 查看当前用户禁用状态
//     * @param id    用户id
//     * @param token 用户token
//     * @return 禁用状态
//     * @throws AuthenticationException
//     */
//    Boolean getBanState(Integer id, String token) throws AuthenticationException;
}
