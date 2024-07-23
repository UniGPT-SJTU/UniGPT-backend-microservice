package com.unigpt.chat.service;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;

import org.springframework.web.multipart.MultipartFile;

import com.unigpt.chat.dto.ResponseDTO;

import javax.naming.AuthenticationException;

public interface KnowledgeService {

    /**
     * 上传文件到知识库
     * 
     * @param userId 用户ID
     * @param isAdmin 是否是管理员
     * @param botId  机器人ID
     * @param file   上传的文件
     * @return
     * @throws AuthenticationException
     */
    ResponseDTO uploadFile(Integer userId, Boolean isAdmin, Integer botId, MultipartFile file) throws AuthenticationException;

    EmbeddingStore<TextSegment> createEmbeddingStore(Integer botId);
}
