package com.unigpt.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.Bot;
import com.unigpt.bot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT b FROM User u JOIN u.starBots b WHERE u.id = :userId")
    Page<Bot> findStarredBotsByUserId(Integer userId, Pageable pageable);

    @Query("SELECT COUNT(b) FROM User u JOIN u.starBots b WHERE u.id = :userId")
    Integer countStarredBotsByUserId(Integer userId);

    @Query("SELECT b FROM User u JOIN u.createBots b WHERE u.id = :userId")
    Page<Bot> findCreatedBotsByUserId(Integer userId, Pageable pageable);

    @Query("SELECT COUNT(b) FROM User u JOIN u.createBots b WHERE u.id = :userId")
    Integer countCreatedBotsByUserId(Integer userId);
}
