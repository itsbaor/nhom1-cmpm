package com.example.socialNetworking.service;

import com.example.socialNetworking.model.BookmarkPost;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface PostsService {
    Posts createPosts(Posts posts, User user);

    List<Posts> getAllPosts(LocalDateTime lastCreatedAt, int size, User user);

    List<Posts> getUserPosts(Long userId);

    Posts findById(Long postId);

    void deletePosts(Posts posts);

    Posts updatePosts(Posts postsDto);

    Posts sharePost(User userReq, PostsRequest postsRequest);

    Posts bookmarkPost(User userReq, Long postId);

    List<BookmarkPost> getAllBookmarkPost(User userReq);

    BookmarkPost deleteBookmarkpost(Long bookmarkId);
}
