package com.example.socialNetworking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "likes_comment")
public class LikeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User user;

    public void setCommentId(Long commentId) {
    }

    public void setUserId(Long id) {

    }
}
