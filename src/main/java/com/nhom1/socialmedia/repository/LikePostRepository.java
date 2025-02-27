package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
}
