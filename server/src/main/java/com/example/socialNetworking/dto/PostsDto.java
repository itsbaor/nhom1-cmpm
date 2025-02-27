package com.example.socialNetworking.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostsDto {
    private Long id;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private UserDto user;

    private int totalLikes;
    private int totalComment;
    private int totalShare;
    private int totalBookmark;

    private boolean liked;
    private boolean shared;
    private boolean bookmarked;

    private List<CommentDto> comments;
    private List<Long> shareUserId;
}
