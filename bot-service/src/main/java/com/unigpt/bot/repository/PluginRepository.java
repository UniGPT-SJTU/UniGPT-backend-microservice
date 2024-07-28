package com.unigpt.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.bot.model.Plugin;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, Integer> {
    
}
