package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByShareUserContainsOrUser_IdOrderByCreatedAtDesc(User shareUser, Long userId);

    @Query("select p from Posts p " +
            "WHERE p.createdAt < :createdAt " +
            "and p not in ( select hp.posts from HiddenPosts hp where hp.user.id = :userId)" +
            "and p.user not in :hiddenUsers")
    Page<Posts> findAllVisiblePostsByCreatedAtBefore(LocalDateTime createdAt,
                                                     Pageable pageable,
                                                     @Param("hiddenUsers") List<User> hiddenUsers,
                                                     @Param("userId") Long userId);

    @Query("select p from Posts p " +
            "where p not in ( select hp.posts from HiddenPosts hp where hp.user.id = :userId)" +
            "and p.user not in :hiddenUsers")
    Page<Posts> findAllVisiblePosts(Pageable pageable,
                                    @Param("hiddenUsers") List<User> hiddenUsers,
                                    @Param("userId") Long userId);
}

