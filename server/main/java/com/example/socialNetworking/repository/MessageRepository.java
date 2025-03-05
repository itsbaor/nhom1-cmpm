package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.ChatRoom;
import com.example.socialNetworking.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomId(Long chatRoomId);
}
