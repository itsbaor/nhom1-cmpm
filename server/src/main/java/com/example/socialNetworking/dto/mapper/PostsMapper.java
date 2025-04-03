package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {UserMapper.class,CommentMapper.class})
public interface PostsMapper {
    PostsMapper INSTANCE = Mappers.getMapper(PostsMapper.class);

    // Map Post -> PostsDto
    @Mapping(target = "totalLikes", expression = "java(posts.getLikes() != null ? posts.getLikes().size() : 0)")
    @Mapping(target = "totalComment", expression = "java(posts.getComments() != null ? posts.getComments().size() : 0)")
    @Mapping(target = "totalShare", expression = "java(posts.getSharedPosts() != null ? posts.getSharedPosts().size() : 0)")
    @Mapping(target = "totalBookmark", expression = "java(posts.getBookmarkPost() != null ? posts.getBookmarkPost().size() : 0)")
    @Mapping(target = "liked", expression = "java(posts.getLikes().stream()" +
            ".anyMatch(like -> like.getUser() != null && like.getUser().getId().equals(req_user.getId())))")
    @Mapping(target = "shared", expression = "java(posts.getSharedPosts().stream()" +
            ".anyMatch(post -> post.getUser().getId().equals(req_user.getId())))")
    @Mapping(target = "bookmarked", expression = "java(posts.getBookmarkPost().stream()" +
            ".anyMatch(bookmark -> bookmark.getUser().getId().equals(req_user.getId())))")
    @Mapping(target = "comments", expression = "java(commentMapper.commentListToCommentDtoList(posts.getComments(), req_user))")
    PostsDto postsToPostsDto(Posts posts, @Context User req_user,@Context CommentMapper commentMapper);

}