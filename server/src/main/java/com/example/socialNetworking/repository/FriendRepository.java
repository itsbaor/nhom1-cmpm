package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.Friend_Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findByUserIdAndFriendId(Long userId, Long friendId);

    Long countByUserId(Long userId);

    List<Friend> findByUserId(Long userId);
}
