package com.example.socialNetworking.dto;

import com.example.socialNetworking.model.ChatRoom;
import com.example.socialNetworking.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;

    private String content;

    private String image;

    private LocalDateTime timestamp;

    private UserDto sender;

    private UserDto receiver;

    private ChatRoomDto chatRoomDto;
}
