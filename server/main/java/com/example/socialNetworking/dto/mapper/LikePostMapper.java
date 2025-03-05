package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.LikePostDto;
import com.example.socialNetworking.model.LikePost;
import com.example.socialNetworking.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {UserMapper.class, PostsMapper.class})
public interface LikePostMapper {

    LikePostMapper Instance = Mappers.getMapper(LikePostMapper.class);

    LikePostDto likeToLikeDto(LikePost like);

}
