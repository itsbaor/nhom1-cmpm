package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.NotificationDto;
import com.example.socialNetworking.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto notificationToNotificationDTO(Notification notification);
}
