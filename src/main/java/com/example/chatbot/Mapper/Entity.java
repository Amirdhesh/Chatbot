package com.example.chatbot.Mapper;

import org.springframework.stereotype.Component;

@Component
public interface Entity<T, S> {
    T toEntity(S s);

}
