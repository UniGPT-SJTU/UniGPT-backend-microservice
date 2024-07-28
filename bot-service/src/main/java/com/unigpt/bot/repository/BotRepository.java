package com.unigpt.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.Bot;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {

    @Query("select b from Bot b where b.name like %?1% and b.isPublished = true order by b.id desc")
    Page<Bot> findBotsByNameContainsAndIsPublishedOrderByIdDesc(String q, Pageable pageable);

    @Query("select b from Bot b where b.isPublished = true order by b.id desc")
    Page<Bot> findBotsByIsPublishedOrderByIdDesc(Pageable pageable);

    @Query("select b from Bot b where b.name like %?1% and b.isPublished = true order by b.likeNumber desc")
    Page<Bot> findBotsByNameContainsAndIsPublishedOrderByLikeNumberDesc(String q, Pageable pageable);

    @Query("select b from Bot b where b.isPublished = true order by b.likeNumber desc")
    Page<Bot> findBotsByIsPublishedOrderByLikeNumberDesc(Pageable pageable);

    @Query("select count(b) from Bot b where b.name like %?1% and b.isPublished = true")
    Integer countByNameContainsAndIsPublished(String q);

    @Query("select count(b) from Bot b where b.isPublished = true")
    Integer countByIsPublished();
}
