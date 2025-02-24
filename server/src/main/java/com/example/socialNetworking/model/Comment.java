package com.example.socialNetworking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private String image;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Posts posts;

    @ManyToOne// Ngăn vòng lặp khi ánh xạ parentComment
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<LikeComment> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)// Ngăn ánh xạ vòng lặp với taggedUsers
    private List<User> taggedUsers = new ArrayList<>();
}
