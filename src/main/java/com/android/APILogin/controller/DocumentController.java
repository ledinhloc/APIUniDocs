package com.android.APILogin.controller;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/list")
    public ResponseData<List<DocumentDto>> getAllDocuments() {
        return new ResponseData<>(HttpStatus.OK.value(), "list document", documentService.getAllDocuments());
    }

    @GetMapping("/search-document")
    public ResponseData<List<DocumentDto>> searchDocument(@RequestParam String keyword) {
        return new ResponseData<>(HttpStatus.OK.value(),"list document search",documentService.searchDocuments(keyword));
    }

    @GetMapping("/filter/{keyword}")
    public ResponseData<List<DocumentDto>> filterDocumentSellPrice(@PathVariable String keyword) {
        if(keyword.isEmpty()){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(),"Không có keyword");
        }
        else{
            if(keyword.equals("desc")){
                return new ResponseData<>(HttpStatus.OK.value(),"list document filter desc",documentService.getDocuemntsSellPriceDesc());
            }
            else{
                return new ResponseData<>(HttpStatus.OK.value(),"list document filter asc",documentService.getDocuemntsSellPriceAsc());
            }

        }
    }
}
