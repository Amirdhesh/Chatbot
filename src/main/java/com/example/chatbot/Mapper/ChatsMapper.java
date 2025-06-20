package com.example.chatbot.Mapper;

import com.example.chatbot.dto.ChatsDTO;
import com.example.chatbot.entity.Chats;

public class ChatsMapper implements DTO<Chats, ChatsDTO> {
    @Override
    public ChatsDTO toDto(Chats chats) {
        ChatsDTO chatsDTO = new ChatsDTO();
        chatsDTO.setId(chats.getId());
        chatsDTO.setTitle(chats.getTitle());
        chatsDTO.setCreatedAt(chats.getCreatedAt());
        return chatsDTO;
    }
}
