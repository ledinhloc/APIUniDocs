package com.android.APILogin.controller;

import com.android.APILogin.dto.request.FileDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.FileChatLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/file-chat-line")
public class FileChatLineController {
    private final FileChatLineService fileChatLineService;

    @PostMapping("/upload")
    public ResponseEntity<Map> upload(FileDto fileDto) {
        try {
            return fileChatLineService.uploadImage(fileDto);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
