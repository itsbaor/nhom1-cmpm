package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
