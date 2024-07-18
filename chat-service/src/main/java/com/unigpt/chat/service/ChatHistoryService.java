package com.unigpt.chat.service;

import java.util.List;

import javax.naming.AuthenticationException;

import com.unigpt.chat.dto.GetChatsOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;

public interface ChatHistoryService {

    // /**
    //  * @brief 删除最后一轮对话，即从最后一个用户消息到最后一个聊天消息的所有对话
    //  * @param historyId 历史id
    //  * @param token 用户token
    //  */
    // void deleteLastRoundOfChats(Integer historyId, String token) throws AuthenticationException;
    // /**
    //  * @brief 在指定历史中加入对话
    //  * @param historyId 历史id
    //  * @param content 对话的内容
    //  * @param type 对话的种类(USER, BOT)
    //  */
    // void createChat(Integer historyId, String content, ChatType type, String token) throws AuthenticationException;
    /**
     * @brief 获取指定历史的所有对话列表
     * @param userId 请求用户的id
     * @param historyId 历史id
     * @param page
     * @param pageSize
     * @return 对话的列表
     */
    GetChatsOkResponseDTO getChats(
            Integer userId,
            Integer historyId,
            Integer page,
            Integer pageSize
    ) throws AuthenticationException;

    /**
     * @brief 获取指定历史的所有对话列表
     * @param userId 请求用户id
     * @param historyid 历史id
     * @return 对话的列表
     */
    List<PromptDTO> getPromptList(
            Integer userId,
            Integer historyid
    ) throws AuthenticationException;

    // /**
    //  * @brief 获取指定历史的历史类
    //  * @param historyId 历史id
    //  * @return 历史类 GetHistoryDTO
    //  */
    // History getHistory(Integer historyId);
    // /**
    //  * @brief 更新历史的最近活跃时间
    //  * @param history
    //  */
    // void updateHistoryActiveTime(History history);
    /**
     * @brief 删除对话历史
     * @param userId 请求用户id
     * @param historyId 删除的历史id
     * @throws Exception
     */
    void deleteHistory(Integer userId, Integer historyId) throws Exception;
}
