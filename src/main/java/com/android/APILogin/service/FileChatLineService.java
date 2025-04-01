package com.android.APILogin.service;

import com.android.APILogin.dto.request.FileDto;
import com.android.APILogin.entity.FileChatLine;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface FileChatLineService {
    //public ResponseEntity<Map> uploadImage(FileChatLine imageModel);

    ResponseEntity<Map> uploadImage(FileDto fileDto);
}
