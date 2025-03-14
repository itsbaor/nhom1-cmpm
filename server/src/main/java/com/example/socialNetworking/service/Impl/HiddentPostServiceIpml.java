package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.HiddenPosts;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.HiddenPostsRepository;
import com.example.socialNetworking.service.HiddentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class HiddentPostServiceIpml implements HiddentPostService {

    private final HiddenPostsRepository hiddenPostsRepository;

    @Override
    public void createHiddenPosts(Posts postsId, User userReq){
        HiddenPosts hiddenPosts = new HiddenPosts();

        hiddenPosts.setUser(userReq);
        hiddenPosts.setPosts(postsId);
        hiddenPosts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        hiddenPostsRepository.save(hiddenPosts);
    }
}
