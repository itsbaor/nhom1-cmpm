package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.CommentException;
import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.LikeComment;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.CommentRepository;
import com.example.socialNetworking.repository.LikeCommentRepository;
import com.example.socialNetworking.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommentServiceImpl implements LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment likeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Not found comment"));
        LikeComment likeComment = likeCommentRepository.findByCommentIdAndUserId(commentId, user.getId());

        if(likeComment != null){
            likeCommentRepository.delete(likeComment);
            return comment;
        }

        LikeComment newLikeComment = new LikeComment();
        newLikeComment.setComment(comment);
        newLikeComment.setUser(user);
        likeCommentRepository.save(newLikeComment);
        comment.getLikes().add(newLikeComment);
        commentRepository.save(comment);

        return comment;

    }
}
