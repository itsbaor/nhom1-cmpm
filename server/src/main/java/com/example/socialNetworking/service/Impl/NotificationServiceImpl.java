package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.Notification;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.NotificationRepository;
import com.example.socialNetworking.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public Notification addNotification(Notification notification) {
        Notification save = notificationRepository.save(notification);
        List<Notification> notifications = notificationRepository.findLatestNotifications(
                notification.getReceiver().getId());

        if(notifications.size() > 10){
            Notification oldestNotification = notifications.get(10);
            notificationRepository.delete(oldestNotification);
        }
        return save;
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        List<Notification> list = notificationRepository.findLatestNotifications(userId)
                .stream().peek(notification -> notification.setSeen(true))
                .collect(Collectors.toList());
        for(Notification notification : list){
            notificationRepository.save(notification);
        }
        return list;
    }

    public Long countUnreadNotifications(Long receiverId) {
        return notificationRepository.countUnreadNotifications(receiverId);
    }

    @Override
    public Notification getNotification(Long postId, User user) {
        return notificationRepository.findNotificationByIdPostsAndSender(postId, user);
    }
}
