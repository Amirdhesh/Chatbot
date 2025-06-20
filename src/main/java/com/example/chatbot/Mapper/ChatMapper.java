package com.example.chatbot.Mapper;

import com.example.chatbot.dto.ChatDTO;
import com.example.chatbot.entity.Chats;

public class ChatMapper implements Entity<Chats, ChatDTO> {
    @Override
    public Chats toEntity(ChatDTO chatDTO) {
        Chats chat = new Chats();
        chat.setTitle(chatDTO.getTitle());
        return chat;
    }
}
