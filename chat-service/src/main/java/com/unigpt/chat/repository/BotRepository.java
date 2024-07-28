package com.unigpt.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.Bot;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {

}
