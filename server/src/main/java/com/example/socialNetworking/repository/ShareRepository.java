package com.example.socialNetworking.repository;


import com.example.socialNetworking.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Posts, Long> {



}
