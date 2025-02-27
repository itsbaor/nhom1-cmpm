package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.User;

import java.util.List;

public interface PostsService {
    public void bookmarkPost(Long userId, Long postId);
    public void unbookmarkPost(Long userId, Long postId);
    public List<User> getBookmarkUsers(Long postId);
}
