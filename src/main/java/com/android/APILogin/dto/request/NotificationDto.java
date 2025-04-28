package com.android.APILogin.dto.request;

import com.android.APILogin.enums.NotificationType;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDto {
    private Long notiId;
    private Long userId;
    private String title;
    private String content;
    private NotificationType type;
    private LocalDateTime createdAt;
    private boolean isRead;
}
