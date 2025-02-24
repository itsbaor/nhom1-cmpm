package com.example.socialNetworking.controller;

import com.example.socialNetworking.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostsService postsService;
    private final UserService userService;

}
