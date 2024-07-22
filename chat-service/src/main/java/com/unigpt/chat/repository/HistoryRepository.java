package com.unigpt.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    @Query("SELECT h FROM History h WHERE h.bot = ?1 AND h.user.id = ?2")
    Page<History> findHistoriesByBotAndUserId(Pageable pageable, Bot bot, Integer userId);

    @Query("SELECT COUNT(h) FROM History h WHERE h.bot = ?1 AND h.user.id = ?2")
    Integer countByBotAndUserId(Bot bot, Integer userId);
}
