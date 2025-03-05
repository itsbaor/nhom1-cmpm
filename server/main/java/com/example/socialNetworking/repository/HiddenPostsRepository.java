package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.HiddenPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddenPostsRepository extends JpaRepository<HiddenPosts, Long> {
}
