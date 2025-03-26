package com.android.APILogin.controller;

import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.entity.ChatLine;
import com.android.APILogin.entity.Conversation;
import com.android.APILogin.entity.User;
import com.android.APILogin.enums.ChatStatus;
import com.android.APILogin.repository.ChatLineRepository;
import com.android.APILogin.repository.ConversationRepository;
import com.android.APILogin.repository.UserRepository;
import com.android.APILogin.service.ChatLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final ChatLineRepository chatLineRepository;
    private final UserRepository userRepository;
    private final ConversationRepository consersationRepository;
    private final ChatLineService chatLineService;

    // gui  /topic/conversation/{conId}
    @MessageMapping("/chat/{conId}")
    @SendTo("/topic/conversation/{conId}")
    public ChatLineDto handleMessage(
            @DestinationVariable String conId,
            ChatLineDto message,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Set additional properties
        //message.setSendAt(LocalDateTime.now());
        //message.setChatStatus(ChatStatus.SENT);
        User user = userRepository.findById(message.getUserId()).orElseThrow();
        Conversation conversation = consersationRepository.findById(message.getConversationId()).orElseThrow();

        ChatLine newChatLine = new ChatLine();
        newChatLine.setUser(user);
        newChatLine.setConversation(conversation);
        newChatLine.setContent(message.getContent());
        newChatLine.setChatStatus(ChatStatus.DELIVERED);
        newChatLine.setSendAt(message.getSendAt());
        newChatLine.setChatLineParentId(message.getChatLineParentId());

        chatLineRepository.save(newChatLine);

        return message;
    }

    @GetMapping("/api/conversations/{conId}/messages")
    public ResponseData<?> getMessagesByConversationId(@PathVariable Long conId) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", chatLineService.getChatLinesByConversationId(conId));
    }
}
