package com.unigpt.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.Memory;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Integer> {

}
