package com.unigpt.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unigpt.chat.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
}
