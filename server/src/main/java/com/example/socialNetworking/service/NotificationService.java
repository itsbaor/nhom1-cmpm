package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Notification;

import java.util.List;

public interface NotificationService {

     Notification addNotification(Notification notification);

     List<Notification> getNotifications(Long userId);

     Long countUnreadNotifications(Long receiverId);
}
