package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.HiddenPosts;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.HiddenPostsRepository;
import com.example.socialNetworking.service.HiddenPostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class HiddenPostsServiceImpl implements HiddenPostsService {

    private final HiddenPostsRepository hiddenPostsRepository;

    @Override
    public void createHiddenPosts(Posts posts, User userReq) {
        HiddenPosts hiddenPosts = new HiddenPosts();
        hiddenPosts.setPosts(posts);
        hiddenPosts.setUser(userReq);
        hiddenPosts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        hiddenPostsRepository.save(hiddenPosts);
    }
}
