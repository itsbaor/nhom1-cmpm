package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.NotificationDto;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.NotificationMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Notification;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.LikePostService;
import com.example.socialNetworking.service.NotificationService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final UserService userService;
    private final LikePostService likePostService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;  // Hỗ trợ gửi thông báo real-time
    private final JwtUtils jwtUtils;  // Hỗ trợ xác thực người dùng từ JWT

    /**
     * API để người dùng like bài viết.
     *
     * @param postId ID của bài viết cần like
     * @param jwt Token xác thực của người dùng
     * @return ResponseEntity chứa thông tin bài viết sau khi like
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostsDto> likePosts(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String jwt) {
        return      }

}
