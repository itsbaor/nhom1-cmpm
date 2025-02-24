package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
