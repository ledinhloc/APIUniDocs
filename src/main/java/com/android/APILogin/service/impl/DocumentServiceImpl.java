package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.DocumentImageMapper;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.DocumentImageDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.mapper.DocumentMapper;
import com.android.APILogin.entity.DocumentImage;
import com.android.APILogin.repository.DocumentImageRepository;
import com.android.APILogin.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentImageRepository documentImageRepository;

    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private DocumentImageMapper documentImageMapper;

    public List<DocumentDto> getAllDocuments() {
        List<DocumentDto> documents = documentRepository.findAllDoc();
        return documents.stream()
                .map(documentDto -> new DocumentDto(documentDto.getDocId(), documentDto.getDocName(), documentDto.getDocImageUrl(), documentDto.getSellPrice(), documentDto.getTotalSold()))
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

    public DocumentDto getDocumentById(Long id) {
        Optional<DocumentDto> documentOtp = documentRepository.findAllDocumentDetail(id);
        DocumentDto document = documentOtp.orElse(null);
        return document;
    }

    public List<DocumentImageDto> getAllImages(Long id) {
        List<DocumentImage> images = documentImageRepository.findAllByDocument_DocId(id);
        return images.stream()
                .map(documentImageMapper::toDocumentImageDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getRelevantDocuments(String type, Long id, Long docId) {
        List<DocumentDto> documents = documentRepository.findDocByTypeAndId(type,id,docId);
        if(documents.isEmpty()) {
            return null;
        }
        return documents.stream()
                .map(documentDto -> new DocumentDto(documentDto.getDocId(), documentDto.getDocName(), documentDto.getDocImageUrl(), documentDto.getSellPrice(), documentDto.getTotalSold()))
                .collect(Collectors.toList());
    }

}
