package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;

import com.example.socialNetworking.dto.*;
import com.example.socialNetworking.dto.mapper.*;

import com.example.socialNetworking.model.*;
import com.example.socialNetworking.service.*;
import lombok.RequiredArgsConstructor;
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

    private final LikePostService likePostService;
    private final LikeCommentService likeCommentService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final PostsService postsService;
    private final JwtUtils jwtUtils;

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostsDto> likePosts(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = likePostService.likePosts(postId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);

        if(!posts.getUser().equals(user)) {
            Notification notification = new Notification();
            notification.setIdPosts(postId);
            notification.setSender(user);
            notification.setReceiver(posts.getUser());
            notification.setTitle(user.getFullName() + " đã thích bài viết của bạn");
            notification.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            notification.setSeen(false);

            Notification save = notificationService.addNotification(notification);
            NotificationDto notificationDto = NotificationMapper.INSTANCE.notificationToNotificationDTO(save);

            //Gửi real-time thong báo
            messagingTemplate.convertAndSendToUser(String.valueOf(posts.getUser().getId()), "/queue/notification/", notificationDto);
        }
        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @GetMapping("/removelike/post/{postId}")
    public ResponseEntity<PostsDto> removelikePosts(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = likePostService.likePosts(postId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @GetMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<PostsDto> likeComment(
            @PathVariable Long postId,@PathVariable Long commentId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postId);
        Comment comment = likeCommentService.likeComment(commentId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);

        if(!comment.getUser().equals(user)){
            Notification notification = new Notification();
            notification.setIdPosts(commentId);
            notification.setSender(user);
            notification.setReceiver(comment.getUser());
            notification.setTitle(user.getFullName() + " đã thích bài viết của bạn");
            notification.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            notification.setSeen(false);

            Notification save = notificationService.addNotification(notification);
            NotificationDto notificationDto = NotificationMapper.INSTANCE.notificationToNotificationDTO(save);

            //Gửi real-time thong báo
            messagingTemplate.convertAndSendToUser(String.valueOf(comment.getUser().getId()),"/queue/notification/", notificationDto);
        }

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @GetMapping("/removelike/comment/{postId}/{commentId}")
    public ResponseEntity<PostsDto> removelikeComment(
            @PathVariable Long postId,@PathVariable Long commentId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postId);
        Comment comment = likeCommentService.likeComment(commentId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }


}
