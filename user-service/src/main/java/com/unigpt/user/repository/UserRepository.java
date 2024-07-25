package com.unigpt.user.repository;

import com.unigpt.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    public Optional<User> findByName(String name);
    public Optional<User> findByAccount(String account);
}
