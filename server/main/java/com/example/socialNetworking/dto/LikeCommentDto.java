package com.example.socialNetworking.dto;

import com.example.socialNetworking.model.Comment;
import lombok.Data;

@Data
public class LikeCommentDto {

    private Long id;

    private CommentDto commentDto;

    private UserDto userDto;
}
