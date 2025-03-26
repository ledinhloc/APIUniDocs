package com.android.APILogin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationOverviewDto {
    private Long conversationId;
    private String displayName;
    private Long unreadCount;
    private LocalDateTime lastMessageTime;
    private String lastMessageContent;
    private String avatarUrl;
}
