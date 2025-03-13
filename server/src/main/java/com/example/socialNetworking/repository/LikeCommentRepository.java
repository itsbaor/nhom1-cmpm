package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.LikeComment;
import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    LikeComment findByComment_IdAndUser_Id(Long commentId, Long userId);
}
