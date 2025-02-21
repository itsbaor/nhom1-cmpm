package com.nhom1.socialmedia.Mapper;

import com.nhom1.socialmedia.dto.MessageDto;
import com.nhom1.socialmedia.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface MessageMapper {

    MessageMapper Instance = Mappers.getMapper(MessageMapper.class);

    MessageDto messageToMessageDto(Message message);
}
