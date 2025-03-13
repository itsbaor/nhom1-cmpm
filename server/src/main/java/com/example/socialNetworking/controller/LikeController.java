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
import com.example.socialNetworking.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller  // Đánh dấu class này là một Controller trong Spring MVC
@RequiredArgsConstructor  // Tự động tạo constructor với các dependency final
@RequestMapping("/api/likes")  // Định nghĩa đường dẫn API chính cho các request liên quan đến like
public class LikeController {

    // Khai báo các service cần thiết
    private final LikePostService likePostService;  // Xử lý like bài viết
    private final LikeCommentService likeCommentService;  // Xử lý like bình luận
    private final UserService userService;  // Quản lý người dùng
    private final SimpMessagingTemplate messagingTemplate;  // Hỗ trợ gửi thông báo real-time
    private final NotificationService notificationService;  // Xử lý thông báo
    private final PostsService postsService;  // Quản lý bài viết
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
        // Lấy email từ token
        String email = jwtUtils.getEmailFromToken(jwt);
        // Lấy thông tin người dùng từ email
        User user = userService.getUserByEmail(email);

        // Gọi service để xử lý like bài viết
        Posts posts = likePostService.likePosts(postId, user);
        // Chuyển đổi đối tượng bài viết sang DTO để trả về frontend
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user, CommentMapper.INSTANCE);

        // Nếu người dùng like bài viết của người khác, tạo thông báo
        if (!posts.getUser().equals(user)) {
            Notification notification = new Notification();
            notification.setIdPosts(postId);
            notification.setSender(user);
            notification.setReceiver(posts.getUser());
            notification.setTitle(user.getFullName() + " đã thích bài viết của bạn");
            notification.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            notification.setSeen(false);

            // Lưu thông báo vào database
            Notification save = notificationService.addNotification(notification);
            // Chuyển đổi thông báo sang DTO
            NotificationDto notificationDto = NotificationMapper.INSTANCE.notificationToNotificationDTO(save);

            // Gửi thông báo real-time đến người nhận
            messagingTemplate.convertAndSendToUser(String.valueOf(posts.getUser().getId()), "/queue/notification/", notificationDto);
        }
        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    /**
     * API để người dùng bỏ like bài viết.
     *
     * @param postId ID của bài viết cần bỏ like
     * @param jwt Token xác thực của người dùng
     * @return ResponseEntity chứa thông tin bài viết sau khi bỏ like
     */
    @GetMapping("/removelike/post/{postId}")
    public ResponseEntity<PostsDto> removelikePosts(@PathVariable("postId") Long postId,
                                                    @RequestHeader("Authorization") String jwt) {
        // Lấy thông tin người dùng từ token
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        // Gọi service để xử lý bỏ like bài viết
        Posts posts = likePostService.likePosts(postId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts, user, CommentMapper.INSTANCE);
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }
    @GetMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<PostsDto> likeComment(@PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postId);
        Comment comment = likeCommentService.likeComment(commentId, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }
    @GetMapping("removelike/comment/{postId}/{commentId}")
    public ResponseEntity<PostsDto> removerLikeComment(@PathVariable Long postId,
                                                       @PathVariable Long commentId,
                                                       @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postId);

        Comment comment = likeCommentService.removeLikeComment(commentId,user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }
}
