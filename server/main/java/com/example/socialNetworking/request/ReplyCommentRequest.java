package com.example.socialNetworking.request;

import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ReplyCommentRequest {
    private Long postId;
    private Long commentId;
    private String content;
    private List<User> taggedUsers;
}
