package com.android.APILogin.dto.request;

import com.android.APILogin.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGroup {
    private NotificationType type;
    private List<NotificationDto> items;
}
