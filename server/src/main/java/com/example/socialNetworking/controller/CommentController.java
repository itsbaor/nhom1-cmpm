package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.CommentDto;
import com.example.socialNetworking.dto.NotificationDto;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.NotificationMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Notification;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;
import com.example.socialNetworking.request.UpdateCommentRequest;
import com.example.socialNetworking.service.CommentService;
import com.example.socialNetworking.service.NotificationService;
import com.example.socialNetworking.service.PostsService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
            @RequestHeader("Authorization") String jwt,
            @RequestBody ReplyCommentRequest reqReply) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = commentService.createReplyComment(reqReply, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user,CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @PostMapping("/Update")
    public ResponseEntity<PostsDto> updateCommentPost(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Comment dto){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = commentService.updateCommentPosts(dto,user);
        //map tu post sang postdto
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user,CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<PostsDto> deleteCommentPosts(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = commentService.deleteCommentPost(id,user);

        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user,CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto,HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public  ResponseEntity <List<CommentDto>> getAllComment(
            @RequestHeader("Authorization") String jwt){

        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Comment> comments = commentService.getAllComment();

        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentDtos.add(CommentMapper.INSTANCE.commentToCommentDto(comment, user));
        }
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
}
