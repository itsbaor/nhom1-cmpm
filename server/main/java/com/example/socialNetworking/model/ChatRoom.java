package com.example.socialNetworking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;
}
