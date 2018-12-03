package com.example.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByOrderByTimestampAsc();
}