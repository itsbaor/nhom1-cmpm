package com.nhom1.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.socialmedia.model.enumType.Status_User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Enumerated(EnumType.ORDINAL)
    private Status_User status;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikePost> likes = new ArrayList<>();
}
