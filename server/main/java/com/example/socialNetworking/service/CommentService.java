package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.request.ReplyCommentRequest;

public interface CommentService {
    Comment createCommentPosts(PostsRequest reqPosts, User user);

    Comment createReplyComment(ReplyCommentRequest reqReply, User user);
}
