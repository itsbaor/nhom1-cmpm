package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.LikeComment;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.CommentRepository;
import com.example.socialNetworking.repository.LikeCommentRepository;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.LikeCommentService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeCommentServiceImpl implements LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public Comment likeComment(Long commentId, User user) {
        // Tìm kiếm bình luận
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Kiểm tra xem người dùng đã like bình luận này chưa
        LikeComment existingLike = likeCommentRepository.findByComment_IdAndUser_Id(commentId, user.getId());

        if (existingLike != null) {
            likeCommentRepository.deleteById(existingLike.getId());
            comment.getLikes().remove(existingLike);
        } else {
            LikeComment likeComment = new LikeComment();
            likeComment.setComment(comment);
            likeComment.setUser(user);

            likeCommentRepository.save(likeComment);
            comment.getLikes().add(likeComment);
        }

        // Cập nhật lại comment
        commentRepository.save(comment);

        // Trả về comment đã cập nhật
        return comment;
    }
}
