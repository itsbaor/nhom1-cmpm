package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;

public interface HiddenPostsService {
    void createHiddenPosts(Posts posts, User userReq);
}
