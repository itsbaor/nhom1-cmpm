package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
