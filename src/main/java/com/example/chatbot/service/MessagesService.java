package com.example.chatbot.service;

import com.example.chatbot.Mapper.MessagesMapper;
import com.example.chatbot.dto.MessageDTO;
import com.example.chatbot.dto.UserMessageDTO;
import com.example.chatbot.entity.Chats;
import com.example.chatbot.entity.Messages;
import com.example.chatbot.repository.ChatsRepo;
import com.example.chatbot.repository.MessagesRepo;
import com.example.chatbot.util.MessageRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessagesService {
    @Autowired
    private MessagesRepo messagesRepo;
    @Autowired
    private ChatsRepo chatsRepo;

    public List<MessageDTO> listMessages(UUID chatId, String email) {
        Chats chat = chatsRepo.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access Denied");
        }
        List<Messages> messages = messagesRepo.findBychatIdAndEmail(chatId, email);
        MessagesMapper mapper = new MessagesMapper();
        List<MessageDTO> messagesDTO = messages.stream().map(mapper::toDto).collect(Collectors.toList());
        return messagesDTO;
    }

    @Transactional
    public List<MessageDTO> write(UserMessageDTO userMessageDTO, String email) {
        Chats chat = chatsRepo.findById(userMessageDTO.getChatId()).orElseThrow(() -> new RuntimeException("Chat not found"));
        if (!chat.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }
        Messages userquery = new Messages();
        userquery.setMessage(userMessageDTO.getQuery());
        userquery.setRole(MessageRole.USER);
        userquery.setChat(chat);
        //String botReply = callLLM(userMessage);

        Messages botResponse = new Messages();
        botResponse.setMessage(reply);
        botResponse.setRole(MessageRole.BOT);
        botResponse.setChat(chat);

        messagesRepo.save(userquery);
        messagesRepo.save(botResponse);
        return messagesRepo.findBychatIdAndEmail(userMessageDTO.getChatId(), email)
                .stream().map(new MessagesMapper()::toDto).toList();
    }
}
