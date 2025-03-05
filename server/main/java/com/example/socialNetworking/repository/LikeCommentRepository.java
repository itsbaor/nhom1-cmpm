package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    LikeComment findByCommentIdAndUserId(Long commentId, Long userId);
}
