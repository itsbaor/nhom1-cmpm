package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Posts> searchPosts(@Param("keyword") String keyword);
}
