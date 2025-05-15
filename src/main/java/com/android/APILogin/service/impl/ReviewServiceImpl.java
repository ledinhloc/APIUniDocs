package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.ReviewCriterialDto;
import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.entity.Review;
import com.android.APILogin.entity.User;
import com.android.APILogin.entity.Document;
import com.android.APILogin.entity.FileMedia;
import com.android.APILogin.enums.FileType;
import com.android.APILogin.repository.ReviewCriterialRepository;
import com.android.APILogin.repository.ReviewRepository;
import com.android.APILogin.repository.DocumentRepository;
import com.android.APILogin.repository.UserRepository;
import com.android.APILogin.service.CloudinaryService;
import com.android.APILogin.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl {
    private final ReviewRepository reviewRepository;
    private final ReviewCriterialRepository reviewCriterialRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final FileUtils fileUtils;

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

    public boolean checkUserPurchasedDocument(Long userId, Long docId) {
        // Kiểm tra xem user đã mua document này chưa
        return documentRepository.existsByDocIdAndOrderDetails_Order_User_UserId(docId, userId);
    }

    public boolean addReview(ReviewDto reviewDto, List<MultipartFile> files) {
        try {
            // Tạo đối tượng Review mới
            Review review = new Review();
            review.setRate(reviewDto.getRate());
            review.setContent(reviewDto.getContent());
            review.setCreatedAt(LocalDateTime.now());
            
            // Set user và document
            User user = userRepository.findById(reviewDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Document document = documentRepository.findById(reviewDto.getDocId())
                    .orElseThrow(() -> new RuntimeException("Document not found"));
            
            review.setUser(user);
            review.setDocument(document);

            // Xử lý upload files nếu có
            if (files != null && !files.isEmpty()) {
                List<FileMedia> fileMedias = new ArrayList<>();
                for (MultipartFile file : files) {


                    // Upload file lên Cloudinary
                    String fileUrl = cloudinaryService.uploadFile(file, "review_images");

                    // Tạo FileMedia và liên kết với review
                    FileMedia fileMedia = new FileMedia();
                    fileMedia.setFileUrl(fileUrl);
                    fileMedia.setFileType(FileType.IMAGE);
                    fileMedia.setReview(review);
                    fileMedias.add(fileMedia);
                }
                review.setFileMedias(fileMedias);
            }

            // Lưu review
            reviewRepository.save(review);
            return true;
        } catch (Exception e) {
            log.error("Error adding review: ", e);
            return false;
        }
    }
}
