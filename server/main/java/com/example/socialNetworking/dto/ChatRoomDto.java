package com.example.socialNetworking.dto;

import lombok.Data;

@Data
public class ChatRoomDto {

    private Long id;

    private UserDto sender;

    private UserDto recipient;
}
