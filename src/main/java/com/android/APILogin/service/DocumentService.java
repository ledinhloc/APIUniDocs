package com.android.APILogin.service;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.mapper.DocumentMapper;
import com.android.APILogin.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    public List<DocumentDto> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }
}
