package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.LikePost;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
        LikePost findByPostsIdAndUserId(Long postsId, Long userId);

        LikePost findByPostsId(Long postId);
}
