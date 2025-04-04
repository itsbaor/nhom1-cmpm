package com.example.socialNetworking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookmarkDto {

    private Long id;

    private UserDto user;

    private PostsDto posts;

    private LocalDateTime createdAt;
}
