package com.example.socialNetworking.request;

import com.example.socialNetworking.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCommentRequest {
    private Long id;
    private Long postId;
    private String content;
    private String image;
}
