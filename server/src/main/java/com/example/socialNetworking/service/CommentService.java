package com.example.socialNetworking.service;


import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;
import com.example.socialNetworking.request.UpdateCommentRequest;

public interface CommentService {
    Posts createCommentPosts(PostsRequest reqPosts, User user);

    Posts createReplyComment(ReplyCommentRequest reqReply, User user);

    Posts updateCommentPosts(Comment dto, User user);
}
