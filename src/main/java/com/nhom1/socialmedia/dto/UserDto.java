package com.nhom1.socialmedia.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String location;
    private String dateOfBirth;
    private String email;
    private String numberPhone;
    private String image;
    private String backgroundImage;
    private String bio;
    private String status;

    private int totalFriends;
}
