package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.ChatLineMapper;
import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.entity.ChatLine;
import com.android.APILogin.repository.ChatLineRepository;
import com.android.APILogin.service.ChatLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatLineServiceImpl implements ChatLineService {
    private final ChatLineRepository chatLineRepository;
    private final ChatLineMapper chatLineMapper;

    @Override
    public List<ChatLineDto> getChatLinesByConversationId(Long conId) {
        List<ChatLine> chatLines = chatLineRepository.findByConversationConId(conId);
        return chatLineMapper.toDtoList(chatLines);
    }
}
