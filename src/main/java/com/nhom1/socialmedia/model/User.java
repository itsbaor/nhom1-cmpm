package com.nhom1.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.socialmedia.model.enumType.Status_User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "user1")

public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
//sua thanh fullname
        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "location")
        private String location;

        @Column(name = "date_of_birth")
        private String dateOfBirth;

        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "number_phone")
        private String numberPhone;

        @Column(name = "image")
        private String image;

        @Column(name = "background_image")
        private String backgroundImage;

        @Column(name = "bio")
        private String bio;

        @Column(name = "req_user")
        private boolean req_user;

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "status")
        private Status_User status;

        @JsonIgnore
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Posts> posts = new ArrayList<>();

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<LikePost> likes = new ArrayList<>();


}
