package com.example.socialNetworking.dto;

import com.example.socialNetworking.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Friend_RequestDto {

    private Long id;

    private UserDto sender;

    private UserDto receiver;

    private LocalDateTime timestamp;

    private boolean isSeen;
}
