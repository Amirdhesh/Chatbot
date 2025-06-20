package com.example.chatbot.entity;

import com.example.chatbot.dto.MessageDTO;
import com.example.chatbot.util.MessageRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private MessageRole role;
    private String message;
    private LocalDateTime createdAt;

    @JoinColumn(name = "chatId")
    @ManyToOne
    @JsonBackReference
    private Chats chat;

    @PrePersist
    public void create() {
        this.createdAt = LocalDateTime.now();
    }
}
