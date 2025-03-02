package com.example.socialNetworking.dto;

import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String image;

    private boolean liked;
    private int totalLikes;

    private UserDto user;

    private CommentDto parentComment;//Binh luan phan hoi

    private List<CommentDto> replies = new ArrayList<>();

    private List<UserDto> taggedUsers = new ArrayList<>();
}
