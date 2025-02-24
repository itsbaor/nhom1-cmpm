package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

}
