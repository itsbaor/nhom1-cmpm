package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.LikeCommentRepository;
import com.nhom1.socialmedia.service.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommentServiceImpl implements LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
}
