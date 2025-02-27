package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.repository.PostsRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService{

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Override
    public void bookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không đúng"));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không dúng"));
        post.getBookmarkUser().add(user);
        userRepository.save(user);
    }

    @Override
    public void unbookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không đúng"));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không dúng"));

        post.getBookmarkUser().remove(user);
        userRepository.save(user);
    }

    @Override
    public List<User> getBookmarkUsers(Long postId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không dúng"));
        return post.getBookmarkUser();
    }
    }
