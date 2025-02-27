package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.repository.PostsRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavePostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postRepository;
    //    phuong thuc luu bai viet
    public void bookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không đúng"));
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không dúng"));
        post.getBookmarkUser().add(user);
        userRepository.save(user);

    }
    //    bo luu bai viet
    public void unbookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.getBookmarkUser().remove(user);
        userRepository.save(user);
    }
    //Lay danh sach bai viet da luu
    public List<User> getBookmarkUsers(Long postId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getBookmarkUser();
    }


}




