package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Override
    Optional<Posts> findById(Long id);
}
