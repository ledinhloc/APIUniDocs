package com.android.APILogin.service.impl;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.mapper.DocumentMapper;
import com.android.APILogin.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
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

    public List<DocumentDto> searchDocuments(String keyword) {
        List<Document> documents = documentRepository.searchDocumentByName(keyword);
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getDocuemntsSellPriceDesc(){
        List<Document> documents = documentRepository.findAllByOrderBySellPriceDesc();
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }
    public List<DocumentDto> getDocuemntsSellPriceAsc(){
        List<Document> documents = documentRepository.findAllByOrderBySellPriceAsc();
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }
}
