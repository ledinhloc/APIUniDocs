package com.android.APILogin.repository;

import com.android.APILogin.dto.request.NotificationDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.entity.Notification;
import com.android.APILogin.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT new com.android.APILogin.dto.request.NotificationDto(" +
            "n.notiId, un.user.userId,n.title, n.content, n.type, n.createdAt, un.isRead) " +
            "FROM Notification n " +
            "JOIN UserNotifi un ON n.notiId = un.notification.notiId " +
            "WHERE un.user.userId = :userId AND ( :type IS NULL OR n.type = :type )")
    List<NotificationDto> findAllNotificationByUserIdAndType(Long userId, NotificationType type);

    boolean existsByTitleAndContentAndType(String title, String content, NotificationType type);
}
