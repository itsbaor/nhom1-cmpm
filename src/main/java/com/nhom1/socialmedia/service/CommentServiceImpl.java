package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.CommentRepository;
import com.nhom1.socialmedia.service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
}
