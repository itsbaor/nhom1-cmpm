package com.example.socialNetworking.dto;

import com.example.socialNetworking.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class FriendDto {

    private Long id;

    private UserDto user;

    private UserDto friend;
}
