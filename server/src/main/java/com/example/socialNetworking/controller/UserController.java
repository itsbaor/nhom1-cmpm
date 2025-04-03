package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.dto.mapper.UserMapper;
import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.*;
import com.example.socialNetworking.util.UserUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final HiddenUsersService hiddenUsersService;
    private final PostsService postsService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Set<Long> onlineUsers = ConcurrentHashMap.newKeySet();

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileByJwt(@RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUserList(@RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        List<User> users = userService.findListUserUnlessOwn(email);

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = UserMapper.Instance.userToUserDto(user);
            userDtos.add(userDto);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserProfileByUserId(@PathVariable("id") Long id,
                                                          @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User req_user = userService.getUserByEmail(email);
        User user = userService.findById(id);

        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(user, req_user));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestHeader("Authorization") String jwt,
                                              @RequestBody User user) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User req = userService.getUserByEmail(email);

        User updateUser = userService.updateUser(req.getId(),user);

        UserDto userDto = UserMapper.Instance.userToUserDto(updateUser);
        userDto.setReq_user(UserUtil.isReqUser(updateUser, req));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{postId}/hiddenUser")
    public ResponseEntity<PostsDto> createHiddenUser(
            @PathVariable Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User userReq = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postId);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,userReq, CommentMapper.INSTANCE);

        hiddenUsersService.createHiddenUser(posts.getUser().getId(), userReq);
        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @MessageMapping("/user.userOnline")
    public UserDto updateUserOnline(@Payload User req){
        User user = userService.updateStatusUser(req.getId());
        UserDto userDto = UserMapper.Instance.userToUserDto(user);

        // Thêm user vào danh sách online
        onlineUsers.add(user.getId());

        List<Friend> friendList = user.getFriends();

        // Gửi thông báo đến tất cả bạn bè
        friendList.parallelStream().forEach(friend -> {
            messagingTemplate.convertAndSendToUser(
                    friend.getFriend().getId().toString(),
                    "/queue/status",
                    userDto
            );
        });

        // Lấy danh sách bạn bè đang online
        List<User> onlineFriends = userService.getUsersByIds(onlineUsers).stream()
                .filter(u -> friendList.stream().anyMatch(f -> f.getUser().getId().equals(u.getId())))
                .collect(Collectors.toList());

        messagingTemplate.convertAndSendToUser(
                req.getId().toString(),
                "/queue/onlineUsers",
                onlineFriends
        );

        return userDto;
    }

    @MessageMapping("/user.userOffline")
    public UserDto updateUserOffline(@Payload User req){
        User user = userService.updateStatusUser(req.getId());
        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        // Xóa user khỏi danh sách online
        onlineUsers.remove(req.getId());

        List<Friend> friendList = user.getFriends();

        // Gửi thông báo đến tất cả bạn bè
        friendList.parallelStream().forEach(friend -> {
            messagingTemplate.convertAndSendToUser(
                    friend.getFriend().getId().toString(),
                    "/queue/status",
                    userDto
            );
        });

        return userDto;
    }
}

