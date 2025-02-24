package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
//    Optional<Posts> findById(Long id);

    @Query(value = "SELECT * FROM posts where (:id is null or id like %:id%) AND (:content is null or content = :content )", nativeQuery = true)
    Page<Posts> findByUserIdAndContent(@Param("id") Long id, @Param("content") String content, Pageable pageable);


}
