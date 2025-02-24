package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {

}

