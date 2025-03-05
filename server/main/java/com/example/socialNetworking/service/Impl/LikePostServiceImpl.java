package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.PostsException;
import com.example.socialNetworking.model.LikePost;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
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

    @Override
    public Posts likePosts(Long postId, User user) throws PostsException {
        LikePost existedLike = likePostRepository.findByPostsIdAndUserId(postId, user.getId());
        Posts posts = postsService.findById(postId);

        if(existedLike != null){
           likePostRepository.deleteById(existedLike.getId());
           return posts;
        }

        LikePost like = new LikePost();
        like.setPosts(posts);
        like.setUser(user);

        likePostRepository.save(like);
        posts.getLikes().add(like);
        postsRepository.save(posts);

        return posts;
    }

    @Override
    public LikePost getALlLikes(Long postId) {
        return likePostRepository.findByPostsId(postId);
    }
}
