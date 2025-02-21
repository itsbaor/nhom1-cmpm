package com.nhom1.socialmedia.dto;

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
