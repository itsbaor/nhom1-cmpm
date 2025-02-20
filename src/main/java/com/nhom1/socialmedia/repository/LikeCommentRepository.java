package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
