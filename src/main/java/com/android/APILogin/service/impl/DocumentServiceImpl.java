package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.DocumentImageMapper;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.DocumentImageDto;
import com.android.APILogin.entity.Category;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.mapper.DocumentMapper;
import com.android.APILogin.entity.DocumentImage;
import com.android.APILogin.entity.User;
import com.android.APILogin.repository.CategoryRepository;
import com.android.APILogin.repository.DocumentImageRepository;
import com.android.APILogin.repository.DocumentRepository;
import com.android.APILogin.repository.UserRepository;
import com.android.APILogin.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentImageRepository documentImageRepository;

    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private DocumentImageMapper documentImageMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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

	public List<DocumentDto> getUserAndCateByDocId(List<Long> docId) {
        List<DocumentDto> documents = documentRepository.findUserAndCateByDocIds(docId);
        if(documents == null || documents.isEmpty()) {
            return null;
        }
        else{
            return documents;
        }
    }
    public DocumentDto pushDocument(DocumentDto documentDto) {
        //mapper
        //Document newDocument = documentMapper.toDocument(documentDto);
        Document newDocument = new Document();
        newDocument.setDocName(documentDto.getDocName());
        newDocument.setDocImageUrl(documentDto.getDocImageUrl());
        newDocument.setSellPrice(documentDto.getSellPrice());
        newDocument.setOriginalPrice(documentDto.getOriginalPrice());
        newDocument.setDocPage(documentDto.getDocPage());
        newDocument.setDocDesc(documentDto.getDocDesc());
        newDocument.setType(documentDto.getType());
        newDocument.setMaxQuantity(documentDto.getMaxQuantity());

        newDocument.setCreatedAt(LocalDateTime.now());

        //set user
        Optional<User> user = userRepository.findById(documentDto.getUserId());
        user.ifPresent(newDocument::setUser);

        //set category
        Optional<Category> categoryOpt = categoryRepository.findById(documentDto.getCateId());
        categoryOpt.ifPresent(newDocument::setCategory);

        //luu document
        Document document = documentRepository.save(newDocument);
        return documentMapper.toDocumentDto(document);
    }

    public List<DocumentDto> getTopNDocuments(Long userId, int n) {
        Pageable page = PageRequest.of(0, n, Sort.by("docId").descending());
        return documentRepository.findByUserWithLimit(userId, page);
    }

    public List<DocumentDto> findAllDocByUserId(Long userId) {
        List<DocumentDto> documents = documentRepository.findAllDocumentByUserId(userId);
        if(documents.isEmpty()) {
            return null;
        }
        else{
            return documents.stream()
                    .map(documentDto -> new DocumentDto(documentDto.getDocId(), documentDto.getDocName(), documentDto.getDocImageUrl(), documentDto.getSellPrice(), documentDto.getTotalSold()))
                    .collect(Collectors.toList());
        }
    }

    public List<DocumentDto> findAllByUserAndTypeSort(@Param("userId") Long userId,@Param("typeSort") String typeSort){
        List<DocumentDto> documents = documentRepository.findByUserAndTypeSort(userId,typeSort);
        if(documents.isEmpty()) {
            return null;
        }
        else{
            return documents.stream()
                    .map(documentDto -> new DocumentDto(documentDto.getDocId(), documentDto.getDocName(), documentDto.getDocImageUrl(), documentDto.getSellPrice(), documentDto.getTotalSold()))
                    .collect(Collectors.toList());
        }
    }
}
