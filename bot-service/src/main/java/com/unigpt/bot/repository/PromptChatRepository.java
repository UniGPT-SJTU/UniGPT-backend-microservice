package com.unigpt.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.PromptChat;

@Repository
public interface PromptChatRepository extends JpaRepository<PromptChat, Integer> {
    
}
