package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.exception.CommentException;
import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.CommentRepository;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;
import com.example.socialNetworking.request.UpdateCommentRequest;
import com.example.socialNetworking.service.CommentService;
import com.example.socialNetworking.service.PostsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;


    @Override
    public Comment createCommentPosts(PostsRequest reqPosts, User user) {
        Posts posts = postsService.findById(reqPosts.getPostId());

        Comment comment = new Comment();
        comment.setContent(reqPosts.getContent());
        comment.setImage(reqPosts.getImage());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        comment.setPosts(posts);

        posts.getComments().add(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Posts updateCommentPosts(Comment dto, User user){

        Comment findComment = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new CommentException("Not found comment"));

        if(dto.getContent() != null && !dto.getContent().trim().isEmpty())
            findComment.setContent(dto.getContent());

        if(dto.getImage() != null && !dto.getImage().trim().isEmpty())
            findComment.setImage(dto.getImage());

        return commentRepository.save(findComment).getPosts();
    }

    @Override
    public Comment createReplyComment(ReplyCommentRequest reqReply, User user) {
        Posts posts = postsService.findById(reqReply.getPostId());
        Comment findComment = commentRepository.findById(reqReply.getCommentId())
                .orElseThrow(() -> new CommentException("Not found comment"));

        Comment comment = new Comment();
        comment.setPosts(posts);
        comment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        comment.setParentComment(findComment);
        comment.setUser(user);
        comment.setTaggedUsers(reqReply.getTaggedUsers());
        comment.setContent(reqReply.getContent());

        if (reqReply.getTaggedUsers() != null) {
            List<User> taggedUsers = reqReply.getTaggedUsers().stream()
                    .map(taggedUser -> userRepository.findById(taggedUser.getId())
                            .orElseThrow(() -> new UserException("Tagged user not found: " + taggedUser.getId())))
                    .toList();
            comment.setTaggedUsers(taggedUsers);
        }

        findComment.getReplies().add(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Posts deleteCommentPost(Long id, User user){
        Comment findComment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentException("Not found comment"));
        Posts post = findComment.getPosts();
        commentRepository.deleteById(id);
        return post;
    }

    @Transactional
    @Override
    public List<Comment> getAllComment(){
       return commentRepository.findAll();
    }
}

