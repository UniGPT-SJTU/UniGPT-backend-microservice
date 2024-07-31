package com.unigpt.user.repository;

import com.unigpt.user.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {
    Optional<Bot> findByTrueId(Integer trueId);
}
