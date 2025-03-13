package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;

public interface HiddentPostService {
    void createHiddenPosts(Posts postsId, User userReq);
}
