package com.unigpt.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.History;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c WHERE c.isVisible = true AND c.history = ?1")
    Page<Chat> findByIsVisibleTrueAndHistory(History history, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Chat c WHERE c.isVisible = true AND c.history = ?1")
    Integer countByIsVisibleTrueAndHistory(History history);
}
