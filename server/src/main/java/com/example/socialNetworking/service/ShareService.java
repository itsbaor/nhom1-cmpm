package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.repository.ShareRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private UserRepository userRepository;
    //chia sẻ bài viết
    public void sharePost(long postId, long userId) {
        Posts post = shareRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài đăng không tồn tại"));
        User user = userRepository.findById(userId)
                .orElseThrow(()  -> new RuntimeException("Người dùng không tồn tại"));
        //thêm người dùng vào danh sách sharuser
        post.getShareUser().add(user);
        shareRepository.save(post);
    }
    //lấy danh sách người dùng chia sẻ bài viết
    public List<User> getShareUsersByPostId(Long postId) {
        Posts post = shareRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getShareUser();
    }
}
