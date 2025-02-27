package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Notification;
import org.aspectj.apache.bcel.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId ORDER BY n.timestamp DESC")
    List<Notification> findLatestNotifications(@Param("receiverId") Long receiverId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.receiver.id = :receiverId AND n.isSeen = false")
    Long countUnreadNotifications(Long receiverId);

}
