package com.example.socialNetworking.service;


import com.example.socialNetworking.model.LikePost;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;

public interface LikePostService {

    Posts likePosts(Long postId, User user);

    LikePost getALlLikes(Long postId);
}
