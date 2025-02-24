package com.nhom1.socialmedia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User friend;

}
