package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.ReviewCriterialDto;
import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.entity.Review;
import com.android.APILogin.repository.ReviewCriterialRepository;
import com.android.APILogin.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewCriterialRepository reviewCriterialRepository;

//    public List<ReviewDto> getAllReviewByDocId(Long docId){
//        List<ReviewDto> reviewDtos = reviewRepository.findReviewByDocumentId(docId);
//        if(reviewDtos.isEmpty()){
//            return null;
//        }
//        for(ReviewDto r : reviewDtos){
//            List<ReviewCriterialDto> rc = reviewCriterialRepository.findCriterialByReviewId(r.getReviewId());
//            r.setCriterialDtos(rc);
//        }
//        return reviewDtos;
//    }

    public List<ReviewDto> getAllReviewByDocId(Long docId) {
        // 1. Lấy danh sách Review từ DB
        List<Review> reviews = reviewRepository.findByDocumentDocId(docId);

        // 2. Map từng Review -> ReviewDto
        return reviews.stream().map(review -> {
            ReviewDto dto = new ReviewDto();
            dto.setReviewId(review.getReviewId());
            dto.setRate(review.getRate());
            dto.setCreatedAt(review.getCreatedAt());
            dto.setContent(review.getContent());

            // Thông tin user
            dto.setUserId(review.getUser().getUserId());
            dto.setName(review.getUser().getName());
            dto.setAvatar(review.getUser().getAvatar());

            // 3. Map ReviewCriterial -> ReviewCriterialDto
            List<ReviewCriterialDto> criterials = review.getReviewCriterials()
                    .stream()
                    .map(rc -> {
                        ReviewCriterialDto cDto = new ReviewCriterialDto();
                        cDto.setId(rc.getId());
                        cDto.setContent(rc.getContent());
                        // Lấy tên tiêu chí từ đối tượng EvaluationCriteria
                        cDto.setName(rc.getEvaluationCriteria().getName());
                        return cDto;
                    })
                    .collect(Collectors.toList());
            dto.setCriterialDtos(criterials);

            // 4. Map FileMedia (entity) sang chính nó hoặc dto nếu bạn có dto
            //    Ở đây giả sử FileMedia entity đã đủ dùng
            dto.setFileMedias(review.getFileMedias());

            return dto;
        }).collect(Collectors.toList());
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
