package com.android.APILogin.service;

import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.entity.ChatLine;

import java.util.List;

public interface ChatLineService {
    List<ChatLineDto> getChatLinesByConversationId(Long conId);
}
