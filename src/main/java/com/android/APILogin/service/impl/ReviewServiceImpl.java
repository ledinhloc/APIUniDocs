package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.ReviewCriterialDto;
import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.repository.ReviewCriterialRepository;
import com.android.APILogin.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewCriterialRepository reviewCriterialRepository;

    public List<ReviewDto> getAllReviewByDocId(Long docId){
        List<ReviewDto> reviewDtos = reviewRepository.findReviewByDocumentId(docId);
        if(reviewDtos.isEmpty()){
            return null;
        }
        for(ReviewDto r : reviewDtos){
            List<ReviewCriterialDto> rc = reviewCriterialRepository.findCriterialByReviewId(r.getReviewId());
            r.setCriterialDtos(rc);
        }
        return reviewDtos;
    }

    public Map<Integer, Long> getCountRateByDocumentId(Long docId){
        List<Object[]> rateCounts = reviewRepository.getCountReviewByDocumentId(docId);
        Map<Integer, Long> map = new HashMap<>();
        for(Object[] r : rateCounts){
            Integer rate = (Integer) r[0];
            Long count = (Long) r[1];
            map.put(rate, count);
        }
        return map;
    }
}
