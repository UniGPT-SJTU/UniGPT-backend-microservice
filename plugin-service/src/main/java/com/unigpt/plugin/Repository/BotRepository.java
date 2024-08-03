package com.unigpt.plugin.Repository;


import com.unigpt.plugin.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer>{
    Optional<Bot> findByTrueId(Integer botid);
}
