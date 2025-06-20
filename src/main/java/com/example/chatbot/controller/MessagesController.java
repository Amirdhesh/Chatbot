package com.example.chatbot.controller;

import com.example.chatbot.dto.MessageDTO;
import com.example.chatbot.dto.UserMessageDTO;
import com.example.chatbot.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class MessagesController {
    @Autowired
    MessagesService messagesService;

    @GetMapping("/{chatId}/Messages")
    public ResponseEntity<List<MessageDTO>> Messages(@PathVariable UUID chatId, Authentication authentication) {
        return new ResponseEntity<>(messagesService.listMessages(chatId, authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("/Message")
    public ResponseEntity<List<MessageDTO>> newMessage(@RequestBody UserMessageDTO userMessageDTO, Authentication authentication) {
        return new ResponseEntity<>(messagesService.write(userMessageDTO, authentication.getName()), HttpStatus.OK);
    }
}
