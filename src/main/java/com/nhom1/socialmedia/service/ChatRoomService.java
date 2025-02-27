package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.ChatRoom;

import java.nio.ByteBuffer;
import java.util.Optional;

public interface ChatRoomService {
    Optional<ChatRoom> findByChatRoom(Long senderId, Long recipientId);
}
