package com.example.chatbot.dto;

import com.example.chatbot.util.MessageRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageDTO {
    private UUID id;
    private MessageRole role;
    private String message;
    private LocalDateTime createdAt;
}
