package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.PostsRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import com.nhom1.socialmedia.service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
}
