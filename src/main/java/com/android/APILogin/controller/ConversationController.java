package com.android.APILogin.controller;

import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.repository.ConversationRepository;
import com.android.APILogin.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/conversation")
@Slf4j
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @GetMapping("/{userId}")
    public ResponseData<?> findConversationsOverview(@PathVariable Long userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", conversationService.findConversationsOverview(userId));
    }
}
