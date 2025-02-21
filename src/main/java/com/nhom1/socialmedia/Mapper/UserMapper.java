package com.nhom1.socialmedia.Mapper;

import com.nhom1.socialmedia.dto.UserDto;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.model.enumType.Status_User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {
    UserMapper Instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "req_user", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    UserDto userToUserDto(User user);

    @Named("statusToString")
    default String statusToString(Status_User status) {
        return status.name();
    }

}

