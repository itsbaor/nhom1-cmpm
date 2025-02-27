package com.nhom1.socialmedia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Long idPosts;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    private LocalDateTime timestamp;

    private boolean isSeen;
}
