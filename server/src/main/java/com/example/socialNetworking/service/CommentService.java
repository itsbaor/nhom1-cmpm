package com.example.socialNetworking.service;


import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;
import com.example.socialNetworking.request.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    Comment createCommentPosts(PostsRequest reqPosts, User user);

    Comment createReplyComment(ReplyCommentRequest reqReply, User user);

    Posts updateCommentPosts(Comment dto, User user);

    Posts deleteCommentPost(Long id, User user);

    List<Comment> getAllComment ();
}
