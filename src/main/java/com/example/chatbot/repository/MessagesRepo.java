package com.example.chatbot.repository;

import com.example.chatbot.entity.Chats;
import com.example.chatbot.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessagesRepo extends JpaRepository<Messages, UUID> {
    @Query(value = "SELECT m.* FROM messages m" +
            " JOIN chats c ON m.chatId=c.id " +
            "JOIN \"user\" u ON c.userId=u.id " +
            "where c.id=:chatId AND u.email=:email", nativeQuery = true)
    List<Messages> findBychatIdAndEmail(@Param("chatId") UUID chatId, @Param("email") String email);
}
