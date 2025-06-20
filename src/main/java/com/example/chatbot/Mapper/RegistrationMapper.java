package com.example.chatbot.Mapper;

import com.example.chatbot.dto.RegisterationDTO;
import com.example.chatbot.entity.User;

public class RegistrationMapper implements Entity<User, RegisterationDTO> {

    @Override
    public User toEntity(RegisterationDTO registerationDTO) {
        User user = new User();
        user.setName(registerationDTO.getName());
        user.setEmail(registerationDTO.getEmail());
        user.setPassword(registerationDTO.getPassword());
        return user;
    }
}
