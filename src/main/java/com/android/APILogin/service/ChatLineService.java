package com.android.APILogin.service;

import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.entity.ChatLine;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ChatLineService {
    List<ChatLineDto> getChatLinesByConversationId(Long conId);
    ChatLineDto handleMessage(ChatLineDto message, List<MultipartFile> files);
    String uploadFile(MultipartFile file);
    Resource downloadFile(String filePath);
    public Resource getFileById(Long fileId);
}
