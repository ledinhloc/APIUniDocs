package com.android.APILogin.controller;

import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.ChatLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final ChatLineService chatLineService;
    private final SimpMessagingTemplate messagingTemplate;

    // gui  /topic/conversation/{conId}
    @MessageMapping("/chat/{conId}")
    @SendTo("/topic/conversation/{conId}")
    public ChatLineDto handleMessage(
            @DestinationVariable String conId,
            ChatLineDto message,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        //xu ly tin nhan
        return chatLineService.handleMessage(message, null);
    }

    @PostMapping("/api/send-chat-picture")
    public ChatLineDto sendChatPicture(
            @RequestPart("message") ChatLineDto message,
            @RequestPart("files") List<MultipartFile> files
    ) {
        // Xử lý tin nhắn
        //message.setFiles(files);//sua sau

        //xu ly tin nhan
        ChatLineDto savedMessage = chatLineService.handleMessage(message, files);

        // Gửi message vừa lưu đến tất cả client WebSocket đang sub conversation này
        messagingTemplate.convertAndSend(
                "/topic/conversation/" + message.getConversationId(),
                savedMessage
        );

        return savedMessage;
    }

    //lay tin nhan
    @GetMapping("/api/conversations/{conId}/messages")
    public ResponseData<?> getMessagesByConversationId(@PathVariable Long conId) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", chatLineService.getChatLinesByConversationId(conId));
    }


    //lay file bang id
    @GetMapping("api/get-file-by-id/{fileId}")
    public ResponseEntity<?> getFileById(@PathVariable Long fileId) {
        try {
            //lay resource
            Resource resource = chatLineService.getFileById(fileId);
            if (resource == null || !resource.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            //InputStream cho phép bạn đọc dữ liệu byte từ tài nguyên.
            InputStream inputStream = resource.getInputStream();
            //đọc toàn bộ nội dung của InputStream và lưu trữ nó trong một mảng byte fileContent
            byte[] fileContent = inputStream.readAllBytes();
            inputStream.close();

            //ten file
            //String filename = relativePath.substring(relativePath.lastIndexOf('/') + 1);
            String filename = resource.getFilename();
            if (filename != null && filename.contains("_")) {
                filename = filename.substring(filename.indexOf("_") + 1);
//                System.out.println(filename); // "Bai tap kiem thu.doc"
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Or determine the correct content type
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(fileContent);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //tai file ve bang url , kqt
//    @GetMapping("/api/get-file")
//    public ResponseEntity<byte[]> getFile(@RequestParam String relativePath) {
//        try {
//            //lay resource
//            Resource resource = chatLineService.downloadFile(relativePath);
//            if (resource == null || !resource.exists()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            //InputStream cho phép bạn đọc dữ liệu byte từ tài nguyên.
//            InputStream inputStream = resource.getInputStream();
//            //đọc toàn bộ nội dung của InputStream và lưu trữ nó trong một mảng byte fileContent
//            byte[] fileContent = inputStream.readAllBytes();
//            inputStream.close();
//
//            //ten file
//            //String filename = relativePath.substring(relativePath.lastIndexOf('/') + 1);
//            String filename = resource.getFilename();
//            if (filename != null && filename.contains("_")) {
//                filename = filename.substring(filename.indexOf("_") + 1);
////                System.out.println(filename); // "Bai tap kiem thu.doc"
//            }
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Or determine the correct content type
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                    .body(fileContent);
//
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //gui file , kqt
//    @PostMapping("/api/post-file")
//    public ResponseData<?> postFile(@RequestPart MultipartFile file)  {
////        chatLineService.uploadFile(file);
//        return new ResponseData<>(HttpStatus.OK.value(), "success", chatLineService.uploadFile(file));
//    }

}
