package com.example.socialNetworking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "likes_post")
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Posts posts;

    @ManyToOne
    private User user;
}
