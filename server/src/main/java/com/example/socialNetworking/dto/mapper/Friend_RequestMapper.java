package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.Friend_RequestDto;
import com.example.socialNetworking.model.Friend_Request;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface Friend_RequestMapper {

    Friend_RequestMapper INSTANCE = Mappers.getMapper(Friend_RequestMapper.class);

    Friend_RequestDto friend_RequestToFriend_RequestDto(Friend_Request friend_request);
}
