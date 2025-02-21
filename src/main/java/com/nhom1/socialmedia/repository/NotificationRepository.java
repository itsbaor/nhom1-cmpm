package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId ORDER BY n.timestamp DESC")
    List<Notification> findLatestNotifications(@Param("receiverId") Long receiverId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.receiver.id = :receiverId AND n.isSeen = false")
    Long countUnreadNotifications(Long receiverId);
}
