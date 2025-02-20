package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.NotificationRepository;
import com.nhom1.socialmedia.service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
}
