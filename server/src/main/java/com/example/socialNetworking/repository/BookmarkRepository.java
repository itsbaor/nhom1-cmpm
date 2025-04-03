package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.BookmarkPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkPost, Long> {

    BookmarkPost findBookmarkPostByUserIdAndPostsId(Long userId, Long postId);

    List<BookmarkPost> findBookmarkPostByUserIdOrderByCreatedAtDesc(Long userId);
}
