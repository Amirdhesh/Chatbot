package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatDTO;
import com.example.chatbot.dto.ChatsDTO;
import com.example.chatbot.entity.Chats;
import com.example.chatbot.service.ChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ChatsController {
    @Autowired
    private ChatsService chatsService;

    @PostMapping("/Chat")
    public Chats newChat(@RequestBody ChatDTO chat) {
        return chatsService.createChat(chat);
    }

    @GetMapping("/Chats")
    public List<ChatsDTO> showChats(Authentication authentication) {
        return chatsService.retrive(authentication.getName());
    }

    @PutMapping("/Chat")
    public ChatsDTO editTitle(@RequestBody ChatsDTO chat, Authentication authentication) {
        return chatsService.changeTitle(chat, authentication.getName());
    }

    @DeleteMapping("/Chat/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable UUID chatId, Authentication authentication) {
        chatsService.delete(chatId, authentication.getName());
        return new ResponseEntity<>("Deleted...", HttpStatus.OK);
    }
}
