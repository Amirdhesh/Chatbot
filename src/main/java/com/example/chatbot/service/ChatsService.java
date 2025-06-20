package com.example.chatbot.service;

import com.example.chatbot.Mapper.ChatMapper;
import com.example.chatbot.Mapper.ChatsMapper;
import com.example.chatbot.dto.ChatDTO;
import com.example.chatbot.dto.ChatsDTO;
import com.example.chatbot.entity.Chats;
import com.example.chatbot.entity.User;
import com.example.chatbot.repository.ChatsRepo;
import com.example.chatbot.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatsService {
    @Autowired
    private ChatsRepo chatsRepo;
    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Chats createChat(ChatDTO chatDTO, String email) {
        User user = userRepo.findByEmail(email);
        Chats chat = new ChatMapper().toEntity(chatDTO);
        chat.setUser(user);
        return chatsRepo.save(chat);
    }

    public List<ChatsDTO> retrive(String email) {
        User user = userRepo.findByEmail(email);
        List<Chats> chats = chatsRepo.findByuserId(user.getId());
        ChatsMapper mapper = new ChatsMapper();
        List<ChatsDTO> chatsDTOList = chats.stream().map(mapper::toDto).collect(Collectors.toList());
        return chatsDTOList;
    }

    public ChatsDTO changeTitle(ChatsDTO chatsDTO, String email) {

        Chats chat = chatsRepo.findById(chatsDTO.getId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        if (!chat.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized deletion attempt");
        }
        chat.setTitle(chatsDTO.getTitle());

        Chats updated = chatsRepo.save(chat);
        ChatsMapper mapper = new ChatsMapper();
        return mapper.toDto(updated);

    }

    public void delete(UUID chatId, String email) {
        Chats chat = chatsRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized deletion attempt");
        }
        chatsRepo.deleteById(chatId);
    }
}
