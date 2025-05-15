package com.android.APILogin.controller;

import com.android.APILogin.dto.request.ChatLineDto;
import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewServiceImpl;

    @GetMapping("/by-document/{docId}")
    public ResponseData<List<ReviewDto>> findReviewByDocumentId(@PathVariable("docId") Long docId) {
        List<ReviewDto> reviews = reviewServiceImpl.getAllReviewByDocId(docId);
        if (reviews == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",reviews);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"review",reviews);
        }
    }

    @PostMapping("/add")
    public ResponseData<Boolean> addReview(
            @RequestPart("reviewDto") ReviewDto reviewDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            // Kiểm tra xem user đã mua document chưa
            boolean hasPurchased = reviewServiceImpl.checkUserPurchasedDocument(reviewDto.getUserId(), reviewDto.getDocId());
            
            if (!hasPurchased) {
                return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "User has not purchased this document", false);
            }

            // Nếu đã mua, tiến hành thêm review và upload file
            boolean result = reviewServiceImpl.addReview(reviewDto, files);
            if (result) {
                return new ResponseData<>(HttpStatus.OK.value(), "Review added successfully", true);
            } else {
                return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add review", false);
            }
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), false);
        }
    }

    @GetMapping("/rate-report/{docId}")
    public ResponseData<Map<Integer, Long>> getRateReport(@PathVariable("docId") Long docId) {
        Map<Integer, Long> rateCount = reviewServiceImpl.getCountRateByDocumentId(docId);
        if (rateCount == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",rateCount);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(),"rate count",rateCount);
        }
    }
}
