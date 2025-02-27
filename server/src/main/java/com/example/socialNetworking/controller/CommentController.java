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
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;
import com.example.socialNetworking.service.CommentService;
import com.example.socialNetworking.service.NotificationService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final UserService userService;
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final JwtUtils jwtUtils;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<PostsDto> createCommmentPosts(@RequestHeader("Authorization") String jwt,
                                                        @RequestBody PostsRequest reqPosts) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = commentService.createCommentPosts(reqPosts, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user, CommentMapper.INSTANCE);

        if(!posts.getUser().equals(user)){
            Notification notification = new Notification();
            notification.setIdPosts(posts.getId());
            notification.setSender(user);
            notification.setReceiver(posts.getUser());
            notification.setTitle(user.getFullName() + " đã bình luận về bài viết của bạn");
            notification.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            notification.setSeen(false);

            Notification save = notificationService.addNotification(notification);
            NotificationDto notificationDto = NotificationMapper.INSTANCE.notificationToNotificationDTO(save);

            //Gửi real-time thong báo
            messagingTemplate.convertAndSendToUser(String.valueOf(posts.getUser().getId()),"/queue/notification/", notificationDto);
        }

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<PostsDto> replyCommentPost(
            @RequestHeader("Authorization") String jwt,@RequestBody ReplyCommentRequest reqReply) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = commentService.createReplyComment(reqReply, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user,CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }


}
