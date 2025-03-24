package com.android.APILogin.controller;

import com.android.APILogin.entity.ChatLine;
import com.android.APILogin.enums.ChatStatus;
import com.android.APILogin.repository.ChatLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private ChatLineRepository chatLineRepository;

    // gui  /topic/conversation/{conId}
    @MessageMapping("/chat/{conId}")
    @SendTo("/topic/conversation/{conId}")
    public ChatLine handleMessage(
            @DestinationVariable String conId,
            ChatLine message,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Set additional properties
        message.setSendAt(LocalDateTime.now());
        //message.setChatStatus(ChatStatus.SENT);

        // Save to database
        chatLineRepository.save(message);
        return message;
    }
}
