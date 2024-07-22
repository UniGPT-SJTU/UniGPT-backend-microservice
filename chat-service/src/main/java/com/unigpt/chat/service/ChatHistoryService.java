package com.unigpt.chat.service;

import java.util.List;

import javax.naming.AuthenticationException;

import com.unigpt.chat.dto.GetChatsOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;

public interface ChatHistoryService {

        /**
         * @brief 获取指定历史的所有对话列表
         * @param userId    请求用户的id
         * @param historyId 历史id
         * @param page
         * @param pageSize
         * @return 对话的列表
         */
        GetChatsOkResponseDTO getChats(
                        Integer userId,
                        Integer historyId,
                        Integer page,
                        Integer pageSize) throws AuthenticationException;

        /**
         * @brief 获取指定历史的所有对话列表
         * @param userId    请求用户id
         * @param historyid 历史id
         * @return 对话的列表
         */
        List<PromptDTO> getPromptList(
                        Integer userId,
                        Integer historyid) throws AuthenticationException;

        /**
         * @brief 删除对话历史
         * @param userId    请求用户id
         * @param historyId 删除的历史id
         * @throws Exception
         */
        void deleteHistory(Integer userId, Integer historyId) throws Exception;
}
