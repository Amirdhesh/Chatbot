package com.example.chatbot.Mapper;

import com.example.chatbot.dto.MessageDTO;
import com.example.chatbot.entity.Messages;

public class MessagesMapper implements DTO<Messages, MessageDTO> {
    @Override
    public MessageDTO toDto(Messages message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setRole(message.getRole());
        messageDTO.setCreatedAt(message.getCreatedAt());
        return messageDTO;
    }
}
