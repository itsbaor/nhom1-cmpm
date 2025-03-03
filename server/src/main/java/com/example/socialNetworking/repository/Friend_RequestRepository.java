package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Friend_Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Friend_RequestRepository extends JpaRepository<Friend_Request, Long> {
    Friend_Request findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Friend_Request> findByReceiverId(Long receiverId);

    List<Friend_Request> findBySenderId(Long senderId);
}
