package com.android.APILogin.controller;

import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/review")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

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
