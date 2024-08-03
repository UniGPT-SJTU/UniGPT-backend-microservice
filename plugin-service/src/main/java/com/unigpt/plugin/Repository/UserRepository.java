package com.unigpt.plugin.Repository;

import com.unigpt.plugin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
<<<<<<< HEAD:user-service/src/main/java/com/unigpt/user/repository/UserRepository.java
//    public Optional<User> findByName(String name);
    public Optional<User> findByAccount(String account);
}
=======
    Optional<User> findByTrueId(Integer userid);
    Optional<User> findByName(String name);
}
>>>>>>> plugin-service/develop:plugin-service/src/main/java/com/unigpt/plugin/Repository/UserRepository.java
