package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.LikeCommentDto;
import com.example.socialNetworking.model.LikeComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, CommentMapper.class})
public interface LikeCommentMapper {

    LikeCommentMapper INSTANCE = Mappers.getMapper(LikeCommentMapper.class);

    LikeCommentDto likeCommentToLikeCommentDto(LikeComment likeComment);
}
