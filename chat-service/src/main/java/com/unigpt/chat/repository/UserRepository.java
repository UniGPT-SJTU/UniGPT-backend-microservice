package com.unigpt.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
