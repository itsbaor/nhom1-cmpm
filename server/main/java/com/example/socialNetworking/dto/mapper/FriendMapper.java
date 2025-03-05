package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.FriendDto;
import com.example.socialNetworking.model.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    FriendDto friendToFriendDto(Friend friend);
}
