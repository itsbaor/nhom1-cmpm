package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Notification;
import com.example.socialNetworking.model.User;

import java.util.List;

public interface NotificationService {

     Notification addNotification(Notification notification);

     List<Notification> getNotifications(Long userId);

     Long countUnreadNotifications(Long receiverId);

     Notification getNotification(Long notificationId, User user);
}
