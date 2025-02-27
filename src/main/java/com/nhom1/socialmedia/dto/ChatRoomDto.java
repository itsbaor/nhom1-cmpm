package com.nhom1.socialmedia.dto;

import lombok.Data;

@Data
public class ChatRoomDto {

    private Long id;

    private UserDto sender;

    private UserDto recipient;
}
