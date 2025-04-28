package com.android.APILogin.controller;

import com.android.APILogin.dto.response.FileDtoResponse;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.FileDocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/file-document")
public class FileDocumentController {
    @Autowired
    private FileDocumentServiceImpl fileDocumentServiceImpl;

    @GetMapping("/by-document/{docId}")
    public ResponseData<FileDtoResponse> getFileByDocumentId(@PathVariable("docId") Long docId) {
        FileDtoResponse fileDtoResponse = fileDocumentServiceImpl.getFileByDocId(docId);
        if (fileDtoResponse == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",fileDtoResponse);
        }
        else {
            return new ResponseData<>(HttpStatus.OK.value(),"file document",fileDtoResponse);
        }
    }
}
