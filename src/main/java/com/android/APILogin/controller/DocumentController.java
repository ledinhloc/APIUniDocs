package com.android.APILogin.controller;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.DocumentImageDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.entity.DocumentImage;
import com.android.APILogin.service.impl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    @Autowired
    private DocumentServiceImpl documentServiceImpl;

    @GetMapping("/list")
    public ResponseData<List<DocumentDto>> getAllDocuments() {
        List<DocumentDto> listDocument = documentServiceImpl.getAllDocuments();
        if(listDocument == null || listDocument.size() == 0) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",listDocument);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "list document", documentServiceImpl.getAllDocuments());
        }
    }

    @GetMapping("/search-document")
    public ResponseData<List<DocumentDto>> searchDocument(@RequestParam String keyword) {
        return new ResponseData<>(HttpStatus.OK.value(),"list document search", documentServiceImpl.searchDocuments(keyword));
    }

    @GetMapping("/filter")
    public ResponseData<List<DocumentDto>> filterDocumentSellPrice(@RequestParam(required = false) String keyword,
                                                                   @RequestParam(required = false) String sortType,
                                                                   @RequestParam(required = false) Long[] categoryIds,
                                                                   @RequestParam(required = false) Double minPrice,
                                                                   @RequestParam(required = false) Double maxPrice,
                                                                   @RequestParam(required = false) Integer[] ratings) {
        List<DocumentDto> listDocument = documentServiceImpl.filterDocuments(keyword, sortType, categoryIds, minPrice, maxPrice, ratings);
        if(listDocument == null || listDocument.size() == 0) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Data not found",listDocument);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"list document filter",listDocument);
        }
    }

    @GetMapping("/document-detail")
    public ResponseData<DocumentDto> getDocumentDetail(@RequestParam Long id) {
        DocumentDto document = documentServiceImpl.getDocumentById(id);
        if(document == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Data not found",document);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"document detail",document);
        }
    }

    @GetMapping("/images/{id}")
    public ResponseData<List<DocumentImageDto>> getAllImageByDocumentId(@PathVariable Long id) {
        List<DocumentImageDto> images = documentServiceImpl.getAllImages(id);
        if(images == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Data not found",images);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"document images",images);
        }
    }

    @GetMapping("/relevance")
    public ResponseData<List<DocumentDto>> getDocumentRelevance(@RequestParam String type, @RequestParam Long id, @RequestParam Long docId) {
        List<DocumentDto> documentDtos = documentServiceImpl.getRelevantDocuments(type,id, docId);
        if(documentDtos == null) {
            return new ResponseData<>(HttpStatus.OK.value(),"Data not found",documentDtos);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"document relevance",documentDtos);
        }
    }

    @PostMapping("/push")
    public ResponseData<DocumentDto> pushDocument(@RequestBody DocumentDto documentDto) {

        return new ResponseData<>(HttpStatus.OK.value(), "success", documentServiceImpl.pushDocument(documentDto));
    }

    @PostMapping("/discount-document")
    public ResponseData<List<DocumentDto>> getDocumentDiscount(@RequestBody List<Long> docIds) {
        List<DocumentDto> documentDto = documentServiceImpl.getUserAndCateByDocId(docIds);
        if(documentDto == null && documentDto.size() == 0) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Data not found",documentDto);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"document discount",documentDto);
        }
    }
}
