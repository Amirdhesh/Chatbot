package com.example.chatbot.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserMessageDTO {
    private UUID chatId;
    private String query;
}
