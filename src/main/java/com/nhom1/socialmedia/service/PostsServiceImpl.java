package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.exception.AppException;
import com.nhom1.socialmedia.exception.ErrorCode;
import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.dto.Requset.PostRequestDto;
import com.nhom1.socialmedia.repository.PostsRepository;
import com.nhom1.socialmedia.service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private static final Logger logger = LoggerFactory.getLogger(PostsServiceImpl.class);

    @Autowired
    PostsRepository postsRepository;

    public ResponseEntity<?> posts(PostRequestDto dto){

        Posts posts = new Posts();
        posts.setContent(dto.getContent());
        //set thoi gian theo gio cua
        posts.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        posts.setImage(dto.getImage());

        try {
            postsRepository.save(posts);
            logger.info(" Created Posts successfully:");
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            logger.error("Error creating user: " + e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id){
        //check id co ton tai khong
        Optional<Posts> idUser = postsRepository.findById(id);

        if(idUser.isEmpty()){
            logger.error("Id không tồn tại");
            return ResponseEntity.badRequest().body(new PostRequestDto(false, "Id không chính xác", null));
        }

        postsRepository.deleteById(id);
        try {
            logger.info(" Delete Posts successfully:");
            return ResponseEntity.ok("User Delete successfully");
        } catch (Exception e) {
            logger.error("Error creating user: " + e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }

    @Override
    public ResponseEntity<?> update(PostRequestDto dto,Long id){

        //check id co ton tai khong
        Optional<Posts> posts = postsRepository.findById(id);

        if(posts.isEmpty()){
            logger.error("Id không tồn tại");
            return ResponseEntity.badRequest().body(new PostRequestDto(false, "Id không chính xác", null));
        }

        Posts updatePost = posts.get();

        updatePost.setImage(dto.getImage());
        updatePost.setContent(dto.getContent());
        updatePost.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        try {
            postsRepository.save(updatePost);
            logger.info(" Update Posts successfully:");
            return ResponseEntity.ok("User Update successfully");
        } catch (Exception e) {
            logger.error("Error creating user: " + e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }
}
