package com.example.socialNetworking.service;


import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.User;

public interface LikeCommentService {
    Comment likeComment(Long commentId, User user);

    Comment removeLikeComment(Long commentId,User user);
}
