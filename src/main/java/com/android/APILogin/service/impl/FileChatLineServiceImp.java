package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.FileDto;
import com.android.APILogin.entity.FileChatLine;
import com.android.APILogin.repository.FileChatLineRepository;
import com.android.APILogin.service.CloudinaryService;
import com.android.APILogin.service.FileChatLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileChatLineServiceImp implements FileChatLineService {
    private final CloudinaryService cloudinaryService;
    private final FileChatLineRepository fileChatLineRepository;

    @Override
    public ResponseEntity<Map> uploadImage(FileDto fileDto) {
        try{
//            if (fileDto.getFile().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }

            FileChatLine fileChatLine = FileChatLine.builder()
                    .build();

            //upload file len cloudinary
            fileChatLine.setFileUrl(cloudinaryService.uploadFile(fileDto.getFile(), "folder_chat"));
            if(fileChatLine.getFileUrl() == null) {
                return ResponseEntity.badRequest().build();
            }

            //lưu file trong csdl
            fileChatLineRepository.save(fileChatLine);
            return ResponseEntity.ok().body(Map.of("url", fileChatLine.getFileUrl()));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
