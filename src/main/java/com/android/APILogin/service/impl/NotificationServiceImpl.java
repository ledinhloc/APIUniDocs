package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.NotificationDto;
import com.android.APILogin.dto.request.NotificationGroup;
import com.android.APILogin.entity.Notification;
import com.android.APILogin.entity.User;
import com.android.APILogin.entity.UserNotifi;
import com.android.APILogin.enums.NotificationType;
import com.android.APILogin.repository.NotificationRepository;
import com.android.APILogin.repository.UserNotifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserNotifiRepository userNotifiRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void pushNotification(NotificationDto notificationDto) {
        pushNotificationLocal(notificationDto);

        String destination = "/queue/notifications/"+notificationDto.getUserId();
        messagingTemplate.convertAndSend(destination, notificationDto);
    }

    public void pushNotificationLocal(NotificationDto notificationDto) {
        Notification notifi = Notification.builder()
                .title(notificationDto.getTitle())
                .content(notificationDto.getContent())
                .type(notificationDto.getType())
                .createdAt(notificationDto.getCreatedAt())
                .build();
        notifi = notificationRepository.save(notifi);

        UserNotifi userNotifi = UserNotifi.builder()
                .notification(notifi)
                .user(User.builder().userId(notificationDto.getUserId()).build())
                .isRead(false)
                .build();
        userNotifiRepository.save(userNotifi);
    }


    public List<NotificationDto> getNotificationsByUserIdAndType(Long userId, NotificationType type) {
        List<NotificationDto> notifications = notificationRepository.findAllNotificationByUserIdAndType(userId, type);
        if(notifications.isEmpty()){
            return null;
        }
        else{
            return notifications;
        }
    }

    public List<NotificationGroup> getNotificationsGroupedByUserId(Long userId) {
        List<NotificationDto> allNotifications = getNotificationsByUserIdAndType(userId, null);

        if (allNotifications.isEmpty()) {
            return Collections.emptyList();
        }

        Map<NotificationType, List<NotificationDto>> grouped = allNotifications.stream()
                .collect(Collectors.groupingBy(NotificationDto::getType));

        return grouped.entrySet().stream()
                .map(entry -> new NotificationGroup(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<NotificationDto> syncNotificationsFromClient(List<NotificationDto> notifications) {
        List<NotificationDto> savedDtos = new ArrayList<>();

        for (NotificationDto dto : notifications) {
            // Kiểm tra đã tồn tại chưa (title + content + type)
            boolean exists = notificationRepository.existsByTitleAndContentAndType(
                    dto.getTitle(), dto.getContent(), dto.getType()
            );

            if (!exists) {
                // Lưu Notification
                Notification notifi = Notification.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .type(dto.getType())
                        .createdAt(dto.getCreatedAt())
                        .build();
                notifi = notificationRepository.save(notifi);

                // Lưu liên kết với User
                UserNotifi userNotifi = UserNotifi.builder()
                        .notification(notifi)
                        .user(User.builder().userId(dto.getUserId()).build())
                        .isRead(dto.isRead())
                        .build();
                userNotifiRepository.save(userNotifi);

                // Gửi realtime nếu cần
                String destination = "/queue/notifications/" + dto.getUserId();
                messagingTemplate.convertAndSend(destination, dto);

                // Thêm vào kết quả trả về
                dto.setNotiId(notifi.getNotiId());
                savedDtos.add(dto);
            }
        }

        return savedDtos;
    }
}
