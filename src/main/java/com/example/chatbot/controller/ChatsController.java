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
    public ResponseEntity<Chats> newChat(@RequestBody ChatDTO chat, Authentication authentication) {
        return new ResponseEntity<>(chatsService.createChat(chat, authentication.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/Chats")
    public ResponseEntity<List<ChatsDTO>> showChats(Authentication authentication) {
        System.out.println(authentication.getName());
        return new ResponseEntity<>(chatsService.retrive(authentication.getName()), HttpStatus.OK);
    }

    @PutMapping("/Chat")
    public ResponseEntity<ChatsDTO> editTitle(@RequestBody ChatsDTO chat, Authentication authentication) {
        return new ResponseEntity<>(chatsService.changeTitle(chat, authentication.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/Chat/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable UUID chatId, Authentication authentication) {
        chatsService.delete(chatId, authentication.getName());
        return new ResponseEntity<>("Deleted...", HttpStatus.OK);
    }
}
