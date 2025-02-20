package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
