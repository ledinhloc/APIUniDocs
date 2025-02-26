package com.android.APILogin.controller;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/list")
    public ResponseData<List<DocumentDto>> getAllDocuments() {
        return new ResponseData<>(HttpStatus.OK.value(), "list document", documentService.getAllDocuments());
    }
}
