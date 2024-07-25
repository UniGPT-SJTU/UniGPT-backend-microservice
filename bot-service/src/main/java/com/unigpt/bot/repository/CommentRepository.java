package com.unigpt.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.bot.id = ?1 order by c.time desc")
    Page<Comment> findByBotIdOrderByTimeDesc(Integer botId, Pageable pageable);

    @Query("select count(c) from Comment c where c.bot.id = ?1")
    Integer countByBotId(Integer botId);

}
