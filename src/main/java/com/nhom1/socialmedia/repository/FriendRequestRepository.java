package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Friend_Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<Friend_Request,Long> {
}
