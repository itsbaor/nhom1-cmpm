package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.dto.mapper.UserMapper;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final HiddenUsersService hiddenUsersService;
    private final PostsService postsService;

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
    public ResponseEntity<UserDto> getUserProfileByUserId(@PathVariable Long id,
                                                          @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User req_user = userService.getUserByEmail(email);
        User user = userService.findById(id);

        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(user, req_user));
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
    @SendTo("/topic/public")
    public UserDto updateUserOnline(@Payload User req){
        User user = userService.updateStatusUser(req.getId());
        return UserMapper.Instance.userToUserDto(user);
    }

    @MessageMapping("/user.userOffline")
    @SendTo("/topic/public")
    public UserDto updateUserOffline(@Payload User req){
        User user = userService.updateStatusUser(req.getId());
        return UserMapper.Instance.userToUserDto(user);
    }
}

