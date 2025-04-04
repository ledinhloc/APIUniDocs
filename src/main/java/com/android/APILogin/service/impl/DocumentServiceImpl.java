package com.android.APILogin.service.impl;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.mapper.DocumentMapper;
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
    private DocumentMapper documentMapper;

    public List<DocumentDto> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream().map(d -> DocumentDto.builder()
                        .docId(d.getDocId())
                        .docName(d.getDocName())
                        .docImageUrl(d.getDocImageUrl())
                        .sellPrice(d.getSellPrice())
                        .build())
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
        Optional<Document> documentOtp = documentRepository.findDocumentByDocId(id);
        Document document = documentOtp.orElse(null);
        DocumentDto documentDto = DocumentDto.builder()
                .docId(document.getDocId())
                .docName(document.getDocName())
                .docImageUrl(document.getDocImageUrl())
                .sellPrice(document.getSellPrice())
                .originalPrice(document.getOriginalPrice())
                .createdAt(document.getCreatedAt())
                .docDesc(document.getDocDesc())
                .docPage(document.getDocPage())
                .download(document.getDownload())
                .type(document.getType())
                .view(document.getView())
                .build();
        return documentDto;
    }

}
