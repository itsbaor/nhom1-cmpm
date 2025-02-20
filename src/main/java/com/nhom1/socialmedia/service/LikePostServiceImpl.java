package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.LikePostRepository;
import com.nhom1.socialmedia.service.service.LikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likePostRepository;
}
