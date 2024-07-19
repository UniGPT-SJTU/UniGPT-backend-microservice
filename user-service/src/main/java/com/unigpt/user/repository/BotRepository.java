package com.unigpt.user.repository;

import com.unigpt.user.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {
//    List<Bot> findAllByOrderByStarNumberDesc();
//
//    List<Bot> findAllByOrderByLikeNumberDesc();
//
//    List<Bot> findAllByOrderByIdDesc();
}
