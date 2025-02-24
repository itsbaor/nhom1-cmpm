package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.exception.AppException;
import com.nhom1.socialmedia.exception.ErrorCode;
import com.nhom1.socialmedia.model.Comment;
import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.model.dto.Requset.CommentRequestDto;
import com.nhom1.socialmedia.model.dto.Requset.PostRequestDto;
import com.nhom1.socialmedia.model.dto.Response.CommentResponseDto;
import com.nhom1.socialmedia.repository.CommentRepository;
import com.nhom1.socialmedia.repository.PostsRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import com.nhom1.socialmedia.service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public ResponseEntity<?> creat(CommentRequestDto dto) {
        try {
            // Kiểm tra xem người dùng có tồn tại không
            Optional<User> userOptional = userRepository.findById(dto.getUserid());
            if (!userOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new PostRequestDto("false", "Id user không chính xác", null));
            }
            // Kiểm tra xem bài viết có tồn tại không
            Optional<Posts> postsOptional = postsRepository.findById(dto.getPostsId());
            if (!postsOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new PostRequestDto("false", "Id Post không chính xác", null));
            }

            Comment comment = new Comment();
            comment.setContent(dto.getContent());
            comment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            comment.setImage(dto.getImage());
            comment.setUser(userOptional.get());
            comment.setPosts(postsOptional.get());

            // Nếu có parent_comment_id, tìm comment cha
            if (dto.getParent_comment_id() != null) {
                Optional<Comment> parentCommentOptional = commentRepository.findById(dto.getParent_comment_id());
                parentCommentOptional.ifPresent(comment::setParentComment);
            }
            commentRepository.save(comment);
            logger.info("Created comment successfully with id: {}", comment.getId());
            return ResponseEntity.ok("Comment created successfully");
        } catch (Exception e) {
            logger.error("Error creating comment: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }

    @Override
    public ResponseEntity<?> delete (Long id){
        try{
            Optional<Comment> commentId = commentRepository.findById(id);
            if(commentId == null){
                return ResponseEntity.badRequest().body(new PostRequestDto("false", "Id không chính xác", null));
            }
            commentRepository.deleteById(id);
            logger.info(" Delete Comment successfully:");
            return ResponseEntity.ok("User Delete Comment successfully");
        }catch (Exception e){
            logger.error("Error creating comment: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }

    @Override
    public ResponseEntity<?> update(CommentResponseDto dto, Long id){
        try{
            Optional<Comment> commentId = commentRepository.findById(id);
            if(commentId == null){
                return ResponseEntity.badRequest().body(new PostRequestDto("false", "Id không chính xác", null));
            }

            Comment updateComment = commentId.get();

            updateComment.setContent(dto.getContent());
            updateComment.setImage(dto.getImage());
            updateComment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

            commentRepository.save(updateComment);
            logger.info(" Update Comment successfully:");
            return ResponseEntity.ok("User Delete Comment successfully");
        }catch (Exception e){
            logger.error("Error creating comment: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }

    @Override
    public ResponseEntity<?> getlist(Long id){
        try{
            if (id != null) {
                Optional<Comment> commentOptional = commentRepository.findById(id);
                if (commentOptional.isEmpty()) {
                    return ResponseEntity.badRequest().body(new PostRequestDto("false", "Id không chính xác", null));
                }
                return ResponseEntity.ok(commentOptional.get());
            }

            List<Comment> comments = commentRepository.findAll();
            return ResponseEntity.ok(comments);
        }catch (Exception e){
            logger.error("Error creating comment: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }
}
