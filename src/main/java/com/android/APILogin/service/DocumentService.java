package com.android.APILogin.service;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.DocumentImageDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentService {
    List<DocumentDto> getAllDocuments();
    List<DocumentDto> searchDocuments(String keyword);
    List<DocumentDto> filterDocuments(@Param("keyword") String keyword,
                                      @Param("sortType") String sortType,
                                      @Param("cateId") Long[] cateIds,
                                      @Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice,
                                      @Param("rating") Integer[] ratings);
    DocumentDto getDocumentById(Long id);
    List<DocumentImageDto> getAllImages(Long id);
    List<DocumentDto> getRelevantDocuments(String type, Long id, Long docId);
}
