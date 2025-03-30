package com.android.APILogin.service;

import com.android.APILogin.dto.response.ConversationOverviewDto;

import java.util.List;

public interface ConversationService {
    List<ConversationOverviewDto> findConversationsOverview(Long userId);
}
