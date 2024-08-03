package com.unigpt.plugin.Repository;

import com.unigpt.plugin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    public Optional<User> findByName(String name);
    Optional<User> findByTrueId(Integer userid);
    Optional<User> findByName(String name);
}
