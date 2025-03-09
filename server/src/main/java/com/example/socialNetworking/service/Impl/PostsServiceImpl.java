package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.PostsException;
import com.example.socialNetworking.model.HiddenUsers;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.HiddenUsersRepository;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.PostsService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final HiddenUsersRepository hiddenUsersRepository;

    @Override
    public void bookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không đúng"));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không dúng"));
        post.getBookmarkUser().add(user);
        userRepository.save(user);
    }

    @Override
    public void unbookmarkPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.getBookmarkUser().remove(user);
        userRepository.save(user);
    }

    @Override
    public List<User> getBookmarkUsers(Long postId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getBookmarkUser();
    }

    @Override
    public Posts createPosts(Posts posts, User user) {
        Posts newPosts = new Posts();
        newPosts.setImage(posts.getImage());
        newPosts.setContent(posts.getContent());
        newPosts.setUser(user);
        newPosts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        return postsRepository.save(newPosts);
    }

    @Override
    public List<Posts> getAllPosts(LocalDateTime lastCreatedAt,int size, User user) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("createdAt").descending());
        List<HiddenUsers> hiddenUsersList = hiddenUsersRepository.findHiddenUsersByUser(user);
        List<User> userList = hiddenUsersList.stream().map(HiddenUsers::getHiddenUser).toList();

        Page<Posts> postPage;
        if (lastCreatedAt == null) {
            // Nếu là lần đầu load, lấy bài mới nhất
            postPage = postsRepository.findAllVisiblePosts(pageable,userList);
        } else {
            // Lấy bài viết có createdAt < lastCreatedAt
            postPage = postsRepository.findAllVisiblePostsByCreatedAtBefore(
                    lastCreatedAt, pageable,userList);
        }
        return postPage.getContent().stream().toList();
    }

    @Override
    public List<Posts> getUserPosts(Long userId) {
        User user = userService.findById(userId);
        return postsRepository.findAllByShareUserContainsOrUser_IdOrderByCreatedAtDesc(user, userId);
    }

    @Transactional
    @Override
    public Posts findById(Long postId) {
        return postsRepository.findById(postId).orElseThrow(() -> new PostsException("not found posts"));
    }

    @Transactional
    @Override
    public void deletePosts(Posts posts) {
        postsRepository.deleteById(posts.getId());
    }

    @Override
    public Posts updatePosts(Posts postsDto) {
        Posts findPost = postsRepository.findById(
                postsDto.getId()).orElseThrow(() -> new PostsException("not found posts"));

        if(postsDto.getImage() != null && !postsDto.getImage().trim().isEmpty()) findPost.setImage(postsDto.getImage());
        if(postsDto.getContent() != null && !postsDto.getContent().trim().isEmpty()) findPost.setContent(postsDto.getContent());

        return postsRepository.save(findPost);
    }
}
