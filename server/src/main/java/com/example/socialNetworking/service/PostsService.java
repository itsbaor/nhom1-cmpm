package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostsService {
    public void bookmarkPost(Long userId, Long postId);

    public void unbookmarkPost(Long userId, Long postId);

    public List<User> getBookmarkUsers(Long postId);

    Posts createPosts(Posts posts, User user);

    List<Posts> getAllPosts(LocalDateTime lastCreatedAt, int size, User user);

    List<Posts> getUserPosts(Long userId);

    Posts findById(Long postId);

    void deletePosts(Posts posts);

    Posts updatePosts(Posts postsDto);

}
