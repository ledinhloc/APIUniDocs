package com.android.APILogin.service.impl;

import com.android.APILogin.dto.response.ConversationOverviewDto;
import com.android.APILogin.repository.ConversationRepository;
import com.android.APILogin.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    @Override
    public List<ConversationOverviewDto> findConversationsOverview(Long userId) {
        return conversationRepository.findConversationsOverview(userId);
    }
}
