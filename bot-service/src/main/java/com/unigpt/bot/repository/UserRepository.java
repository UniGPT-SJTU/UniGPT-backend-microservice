package com.unigpt.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
