package com.example.socialNetworking.service;

import com.example.socialNetworking.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomService {
    Optional<ChatRoom> findByChatRoom(Long senderId, Long receiverId);
}
