package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Posts, Long> {



}
