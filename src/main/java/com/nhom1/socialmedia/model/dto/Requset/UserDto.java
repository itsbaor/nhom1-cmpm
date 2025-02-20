package com.nhom1.socialmedia.model.dto.Requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.socialmedia.model.enumType.Status_User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String location;
    private String dateOfBirth;
    private String email;
    private String password;
    private String numberPhone;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;

    private Status_User status;


//    @JsonIgnore
//    private List<Posts> posts;
//
//    private List<LikePost> likes;
}
