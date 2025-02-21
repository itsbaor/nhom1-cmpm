package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.Mapper.NotificationMapper;
import com.nhom1.socialmedia.config.jwt.JwtUtils;
import com.nhom1.socialmedia.dto.NotificationDto;
import com.nhom1.socialmedia.model.Notification;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.service.NotificationService;
import com.nhom1.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/listNotify/{userId}")
    public ResponseEntity<List<NotificationDto>> getAllNotification(@PathVariable Long userId) {
        List<Notification> list = notificationService.getNotifications(userId);
        List <NotificationDto> dtos = new ArrayList<>();
        for(Notification notification : list){
            NotificationDto dto = NotificationMapper.INSTANCE.notificationToNotificationDTO(notification);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/unread-notify")
    public ResponseEntity<Long> getUnreadNotificationCount(@RequestHeader("Authorization") String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Long unreadCount = notificationService.countUnreadNotifications(user.getId());
        return ResponseEntity.ok(unreadCount);
    }
}
