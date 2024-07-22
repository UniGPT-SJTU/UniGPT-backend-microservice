package com.unigpt.chat.service;

import java.util.List;

import com.unigpt.chat.dto.BotEditInfoDTO;
import com.unigpt.chat.dto.CreateBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.GetBotHistoryOkResponseDTO;
import com.unigpt.chat.dto.PromptDTO;

public interface BotService {

    // /**
    //  * @brief 获取机器人列表
    //  * @param q 搜索关键字
    //  * @param order 排序方式
    //  * @param page 页码
    //  * @param pageSize 每页大小
    //  * @return 机器人列表
    //  */
    // GetBotsOkResponseDTO getBots(String q, String order, Integer page, Integer pageSize);
    // /**
    //  * @brief 获取机器人简要信息
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 机器人简要信息
    //  */
    // BotBriefInfoDTO getBotBriefInfo(Integer id, String token);
    // /**
    //  * @brief 获取机器人详细信息
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 机器人详细信息
    //  */
    // BotDetailInfoDTO getBotDetailInfo(Integer id, String token);
    // /**
    //  * @brief 获取机器人编辑信息
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 机器人编辑信息
    //  */
    // BotEditInfoDTO getBotEditInfo(Integer id, String token);
    /**
     * @brief 创建机器人
     * @param dto 机器人编辑信息
     * @param token 用户token
     * @return 创建结果
     */
    void createBot(Integer userId, Integer id, BotEditInfoDTO dto) throws Exception;
    /**
     * @brief 更新机器人
     * @param id 机器人id
     * @param dto 机器人编辑信息
     * @param token 用户token
     * @return 更新结果
     */
    void updateBot(Integer userId, Boolean isAdmin, Integer id, BotEditInfoDTO dto) throws Exception;
//     /**
//      * @brief 删除机器人
//      * @param id 机器人id
//      * @param token 用户token
//      * @return 删除结果
//      */
    // ResponseDTO likeBot(Integer id, String token);
    // /**
    //  * @brief 取消点赞机器人
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 取消点赞结果
    //  */
    // ResponseDTO dislikeBot(Integer id, String token);
    // /**
    //  * @brief 收藏机器人
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 收藏结果
    //  */
    // ResponseDTO starBot(Integer id, String token);
    // /**
    //  * @brief 取消收藏机器人
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @return 取消收藏结果
    //  */
    // ResponseDTO unstarBot(Integer id, String token);
    /**
     * @brief 获取机器人历史记录
     * @param userId 请求用户id
     * @param botId 机器人id
     * @param page 页码
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
     * @param userId 用户id
     * @param botId 机器人id
     * @param promptList prompt记录内容
     * @return 添加结果
     */
    CreateBotHistoryOkResponseDTO createBotHistory(
            Integer userId,
            Integer botId,
            List<PromptDTO> promptList) throws Exception;
    // /**
    //  * s
    //  *
    //  * @brief 获取机器人评论
    //  * @param id 机器人id
    //  * @param page 页码
    //  * @param pageSize 每页大小
    //  * @return 机器人评论
    //  */
    // GetCommentsOkResponseDTO getComments(Integer id, Integer page, Integer pageSize);
    // /**
    //  * @brief 创建评论
    //  * @param id 机器人id
    //  * @param token 用户token
    //  * @param content 评论内容
    //  * @return 创建结果
    //  */
    // ResponseDTO createComment(Integer id, String token, String content);
}
