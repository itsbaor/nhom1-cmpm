package com.example.socialNetworking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private String image;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Posts originalPost; // nếu != null thì đây là 1 bài share

    @OneToMany(mappedBy = "originalPost", cascade = CascadeType.ALL)
    private List<Posts> sharedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<LikePost> likes = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<User> shareUser = new ArrayList<>();

    @ManyToMany
    private List<User> bookmarkUser = new ArrayList<>();
}
