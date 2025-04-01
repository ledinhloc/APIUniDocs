package com.android.APILogin.controller;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/list")
    public ResponseData<List<DocumentDto>> getAllDocuments() {
        List<DocumentDto> listDocument = documentService.getAllDocuments();
        if(listDocument == null || listDocument.size() == 0) {
            return new ResponseData<>(HttpStatus.OK.value(),"list document filter",listDocument);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "list document", documentService.getAllDocuments());
        }
    }

    @GetMapping("/search-document")
    public ResponseData<List<DocumentDto>> searchDocument(@RequestParam String keyword) {
        return new ResponseData<>(HttpStatus.OK.value(),"list document search",documentService.searchDocuments(keyword));
    }

    @GetMapping("/filter")
    public ResponseData<List<DocumentDto>> filterDocumentSellPrice(@RequestParam(required = false) String keyword,
                                                                   @RequestParam(required = false) String sortType,
                                                                   @RequestParam(required = false) List<Long> categoryIds,
                                                                   @RequestParam(required = false) Double minPrice,
                                                                   @RequestParam(required = false) Double maxPrice,
                                                                   @RequestParam(required = false) List<Integer> ratings) {
        List<DocumentDto> listDocument = documentService.filterDocuments(keyword, sortType, categoryIds, minPrice, maxPrice, ratings);
        if(listDocument == null || listDocument.size() == 0) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Data not found",listDocument);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"list document filter",listDocument);
        }
    }
}
