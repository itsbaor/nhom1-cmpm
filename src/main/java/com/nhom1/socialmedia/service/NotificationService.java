package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification addNotification(Notification notification);

    List<Notification> getNotifications(Long userId);

    Long countUnreadNotifications(Long receiverId);
}
