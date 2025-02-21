package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
