package com.android.APILogin.repository;

import com.android.APILogin.dto.request.ReviewDto;
import com.android.APILogin.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT new com.android.APILogin.dto.request.ReviewDto(" +
            "r.reviewId, r.rate, r.createdAt, r.content, u.userId, u.name, u.avatar, null) " +
            "FROM Review r " +
            "JOIN User u ON r.user.userId = u.userId " +
            "WHERE r.document.docId = :docId")
    List<ReviewDto> findReviewByDocumentId(@Param("docId") Long docId);

    @Query(value="SELECT r.rate, COUNT(r) FROM Review r WHERE r.document.docId = :docId GROUP BY r.rate")
    List<Object[]> getCountReviewByDocumentId(@Param("docId") Long docId);
}
