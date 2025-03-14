package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.CommentDto;
import com.example.socialNetworking.model.Comment;
import com.example.socialNetworking.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {UserMapper.class, PostsMapper.class,})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "totalLikes", expression = "java(comment.getLikes() != null ? comment.getLikes().size() : 0)")
    @Mapping(target = "liked", expression = "java(comment.getLikes().stream()" +
            ".anyMatch(like -> like.getUser() != null && like.getUser().getId().equals(req_user.getId())))")
    @Mapping(target = "parentComment", expression = "java(comment.getParentComment() != null ? commentToMinimalDto(comment.getParentComment()) : null)")
    @Mapping(target = "postId", source = "posts.id")
    CommentDto commentToCommentDto(Comment comment,@Context User req_user);

    // Mapping danh sách Comment sang danh sách CommentDto
    default List<CommentDto> commentListToCommentDtoList(List<Comment> comments, @Context User req_user) {
        if (comments == null) {
            return null;
        }
        return comments.stream()
                .map(comment -> commentToCommentDto(comment, req_user))
                .collect(Collectors.toList());
    }

    default CommentDto commentToMinimalDto(Comment parentComment) {
        CommentDto dto = new CommentDto();
        dto.setId(parentComment.getId());
        dto.setContent(parentComment.getContent());
        return dto;
    }
}
