package com.nhom1.socialmedia.Mapper;

import com.nhom1.socialmedia.dto.NotificationDto;
import com.nhom1.socialmedia.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto notificationToNotificationDTO(Notification notification);
}
