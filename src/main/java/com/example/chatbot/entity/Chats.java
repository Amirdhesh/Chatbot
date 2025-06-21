package com.example.chatbot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chats {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Messages> messages;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}
