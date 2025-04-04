package com.example.socialNetworking.service.Impl;


import com.example.socialNetworking.exception.PostsException;
import com.example.socialNetworking.model.BookmarkPost;
import com.example.socialNetworking.model.HiddenUsers;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.BookmarkRepository;
import com.example.socialNetworking.repository.HiddenUsersRepository;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.PostsRequest;
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
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final HiddenUsersRepository hiddenUsersRepository;

    @Override
    public Posts createPosts(Posts posts, User user) {
        if(posts.getContent() == null || posts.getContent().isEmpty()) {
            throw new RuntimeException("Content is empty");
        }
        Posts newPosts = new Posts();
        newPosts.setImage(posts.getImage());
        newPosts.setContent(posts.getContent());
        newPosts.setUser(user);
        newPosts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        return postsRepository.save(newPosts);
    }

    @Override
    public List<Posts> getAllPosts(LocalDateTime lastCreatedAt, int size, User user) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("createdAt").descending());
        List<HiddenUsers> hiddenUsersList = hiddenUsersRepository.findHiddenUsersByUser(user);
        List<User> userList = hiddenUsersList.stream().map(HiddenUsers::getHiddenUser).toList();

        Page<Posts> postPage;
        if (lastCreatedAt == null) {
            // Nếu là lần đầu load, lấy bài mới nhất
            postPage = postsRepository.findAllVisiblePosts(userList,pageable);
        } else {
            // Lấy bài viết có createdAt < lastCreatedAt
            postPage = postsRepository.findAllVisiblePostsByCreatedAtBefore(
                    lastCreatedAt,userList, pageable);
        }
        return postPage.getContent().stream().toList();
    }

    @Override
    public List<Posts> getUserPosts(Long userId) {
        User user = userService.findById(userId);
        return postsRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
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

    @Override
    public Posts sharePost(User userReq, PostsRequest postsRequest) {
        Posts findPost = postsRepository.findById(
                postsRequest.getPostId()).orElseThrow(() -> new PostsException("not found posts"));

        Posts newPosts = new Posts();
        if(!findPost.getSharedPosts().stream().anyMatch(post -> post.getUser().getId().equals(userReq.getId()))){
            newPosts.setContent(postsRequest.getContent());
            newPosts.setUser(userReq);
            newPosts.setOriginalPost(findPost);
            newPosts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        }

        return postsRepository.save(newPosts);
    }

    @Override
    public Posts bookmarkPost(User userReq, Long postId) {
        Posts findPost = postsRepository.findById(
                postId).orElseThrow(() -> new PostsException("not found posts"));
        BookmarkPost bookmarkPost = bookmarkRepository.findBookmarkPostByUserIdAndPostsId(userReq.getId(), postId);

        if(bookmarkPost != null){
            // Xóa khỏi database
            bookmarkRepository.delete(bookmarkPost);
        }else {
            BookmarkPost newBookmark = new BookmarkPost();
            newBookmark.setPosts(findPost);
            newBookmark.setUser(userReq);
            newBookmark.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

            bookmarkRepository.save(newBookmark);
        }

        // Fetch lại findPost để lấy danh sách bookmarkPost mới nhất
        return postsRepository.findById(postId).orElseThrow();
    }

    @Override
    public List<BookmarkPost> getAllBookmarkPost(User userReq) {
        return bookmarkRepository.findBookmarkPostByUserIdOrderByCreatedAtDesc(userReq.getId());
    }

    @Override
    public BookmarkPost deleteBookmarkpost(Long bookmarkId) {
        BookmarkPost findBookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new PostsException("not found post"));
        postsRepository.findById(
                findBookmark.getPosts().getId()).orElseThrow(() -> new PostsException("not found posts"));
        bookmarkRepository.deleteById(bookmarkId);
        return findBookmark;
    }
}
