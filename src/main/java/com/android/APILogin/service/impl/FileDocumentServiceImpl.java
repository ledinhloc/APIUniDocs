package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.FileDocumentMapper;
import com.android.APILogin.dto.response.FileDtoResponse;
import com.android.APILogin.entity.FileDocument;
import com.android.APILogin.repository.FileDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileDocumentServiceImpl {
    @Autowired
    private FileDocumentRepository fileDocumentRepository;

    @Autowired
    private FileDocumentMapper fileDocumentMapper;

    public FileDtoResponse getFileByDocId(Long docId) {
        Optional<FileDocument> fileDocument = fileDocumentRepository.findFileDtoByDocId(docId);
        FileDtoResponse fileDtoResponse = fileDocument.map(fileDocumentMapper::toDto).orElse(null);
        return fileDtoResponse;
    }
}
