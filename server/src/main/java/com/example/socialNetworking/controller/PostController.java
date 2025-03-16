package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.CommentMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.PostsService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostsService postsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<PostsDto> createPosts(@RequestHeader("Authorization") String jwt,
                                                @RequestBody Posts posts){
        try {
            String email = jwtUtils.getEmailFromToken(jwt);
            User user = userService.getUserByEmail(email);

            Posts createPost =  postsService.createPosts(posts, user);
            PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(createPost, user, CommentMapper.INSTANCE);
            return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<PostsDto>> getAllPosts(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(name = "lastCreatedAt", required = false) LocalDateTime lastCreatedAt,
            @RequestParam("size") int size){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Posts> posts = postsService.getAllPosts(lastCreatedAt,size,user);

        //doi sang dto
        List<PostsDto> postsDtos = new ArrayList<>();
        for(Posts post : posts){
            postsDtos.add(PostsMapper.INSTANCE.postsToPostsDto(post, user, CommentMapper.INSTANCE));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @GetMapping("/{postsId}")
    public ResponseEntity<PostsDto> getPostsByPostsId(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long postsId){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postsId);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user, CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);

    }

    @GetMapping("/user/{postsId}")
    public ResponseEntity<List<PostsDto>> getUserPosts(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long postsId){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Posts> posts = postsService.getUserPosts(postsId);
        List<PostsDto> postsDtos = new ArrayList<>();

        for(Posts post : posts){
            postsDtos.add(PostsMapper.INSTANCE.postsToPostsDto(post, user, CommentMapper.INSTANCE));
        }

        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostsDto> deletePost(
            @PathVariable Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User userReq = userService.getUserByEmail(email);
        Posts posts = postsService.findById(postId);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,userReq, CommentMapper.INSTANCE);
        postsService.deletePosts(posts);
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<PostsDto> updatePost(
            @RequestBody Posts postsreq, @RequestHeader("Authorization") String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User userReq = userService.getUserByEmail(email);

        Posts posts =  postsService.updatePosts(postsreq);
        PostsDto updatePosts = PostsMapper.INSTANCE.postsToPostsDto(posts,userReq, CommentMapper.INSTANCE);
        return new ResponseEntity<>(updatePosts, HttpStatus.OK);
    }

    @PostMapping("/sharepost")
    public ResponseEntity<PostsDto> sharePost(
            @RequestHeader("Authorization") String jwt, @RequestBody PostsRequest postsRequest){
        String email = jwtUtils.getEmailFromToken(jwt);
        User userReq = userService.getUserByEmail(email);

        Posts posts = postsService.sharePost(userReq, postsRequest);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,userReq, CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<PostsDto> bookmarkPost(
            @PathVariable Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User userReq = userService.getUserByEmail(email);

        Posts posts = postsService.bookmarkPost(userReq, postId);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,userReq, CommentMapper.INSTANCE);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }


}
