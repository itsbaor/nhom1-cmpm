package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.repository.LikePostRepository;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.service.LikePostService;
import com.example.socialNetworking.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostsRepository postsRepository;
    private final PostsService postsService;


}
