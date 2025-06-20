package com.example.chatbot.Mapper;

import org.springframework.stereotype.Component;

@Component
public interface DTO<T, S> {
    S toDto(T t);
}
