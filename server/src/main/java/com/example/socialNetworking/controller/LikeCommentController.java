package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.CommentDto;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likesComment")
public class LikeCommentController {
    private final JwtUtils jwtUtils;
    private final LikePostService likePostService;
    private final LikeCommentService likeCommentService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final PostsService postsService;

    @PostMapping("/api/likecomment/{commentId}")
    public ResponseEntity<CommentDto> like(@PathVariable Long commentId,
                                           @RequestHeader("Authorization") String jwt) {

        // Lấy thông tin người dùng từ token
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        // Gọi service để xử lý việc like bình luận
        Comment comment = likeCommentService.likeComment(commentId,user);

        // Chuyển đổi comment thành CommentDto
        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(comment, user);

        // Trả về CommentDto
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
}
