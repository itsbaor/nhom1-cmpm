package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Notification;
import com.nhom1.socialmedia.repository.NotificationRepository;
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
}
