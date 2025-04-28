package com.android.APILogin.controller;

import com.android.APILogin.dto.request.NotificationDto;
import com.android.APILogin.dto.request.NotificationGroup;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.enums.NotificationType;
import com.android.APILogin.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationService;

    @PostMapping("/push")
    public ResponseData<Void> pushNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.pushNotification(notificationDto);
        return new ResponseData<>(HttpStatus.OK.value(), "Notification has been pushed via WebSocket", null);
    }

    @PostMapping("/push/local")
    public ResponseData<Void> pushNotificationLocal(@RequestBody NotificationDto dto) {
        notificationService.pushNotificationLocal(dto);
        return new ResponseData<>(HttpStatus.OK.value(), "Notification has been saved locally", null);
    }

    @GetMapping("/user-type")
    public ResponseData<List<NotificationDto>> getNotificationsByUserId(@RequestParam Long userId,
                                                                        @RequestParam(required = false) NotificationType type) {
        List<NotificationDto> notificationDtos = notificationService.getNotificationsByUserIdAndType(userId,type);
        if(notificationDtos.isEmpty()){
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",notificationDtos);
        }
        else {
            return new ResponseData<>(HttpStatus.OK.value(),"list notification",notificationDtos);
        }
    }
    @GetMapping("/grouped")
    public ResponseData<List<NotificationGroup>> getNotificationsGrouped(
            @RequestParam Long userId) {
        List<NotificationGroup> groups = notificationService.getNotificationsGroupedByUserId(userId);
        if (groups.isEmpty()) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "No notifications found", groups);
        }
        return new ResponseData<>(HttpStatus.OK.value(), "Grouped notifications", groups);
    }

    @PostMapping("/sync")
    public ResponseData<List<NotificationDto>> syncNotifications(@RequestBody List<NotificationDto> notifications) {
        List<NotificationDto> savedNotis = notificationService.syncNotificationsFromClient(notifications);
        if(savedNotis.isEmpty()){
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",savedNotis);
        }
        return new ResponseData<>(HttpStatus.OK.value(), "Đồng bộ thành công", savedNotis);
    }

}
