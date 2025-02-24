package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.repository.CommentRepository;
import com.example.socialNetworking.repository.LikeCommentRepository;
import com.example.socialNetworking.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommentServiceImpl implements LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

}
