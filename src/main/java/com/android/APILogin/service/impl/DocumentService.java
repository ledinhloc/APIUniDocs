package com.android.APILogin.service.impl;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.mapper.DocumentMapper;
import com.android.APILogin.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
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

    public List<DocumentDto> filterDocuments(@Param("keyword") String keyword,
                                             @Param("sortType") String sortType,
                                             @Param("cateId") Long[] cateIds,
                                             @Param("minPrice") Double minPrice,
                                             @Param("maxPrice") Double maxPrice,
                                             @Param("rating") Integer[] ratings) {
        LocalDateTime day = null;
        if(sortType.equals("newest")) {
            day = LocalDateTime.now().minusDays(7);
        }
        List<Document> documents = documentRepository.filterDocument(keyword,sortType,cateIds,minPrice,maxPrice,ratings,day);
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .collect(Collectors.toList());
    }

}
