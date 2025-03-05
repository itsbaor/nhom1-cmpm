package com.example.socialNetworking.dto;

import lombok.Data;

@Data
public class LikePostDto {
    private Long id;

    private PostsDto postsDto;

    private UserDto userDto;
}
