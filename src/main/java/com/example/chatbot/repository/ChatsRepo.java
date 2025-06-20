package com.example.chatbot.repository;

import com.example.chatbot.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatsRepo extends JpaRepository<Chats, UUID> {
    List<Chats> findByuserId(UUID userId);
}
