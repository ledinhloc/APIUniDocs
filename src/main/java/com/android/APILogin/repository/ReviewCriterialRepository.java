package com.android.APILogin.repository;

import com.android.APILogin.dto.request.ReviewCriterialDto;
import com.android.APILogin.entity.ReviewCriterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewCriterialRepository extends JpaRepository<ReviewCriterial, Long> {
    @Query(value = "SELECT NEW com.android.APILogin.dto.request.ReviewCriterialDto(" +
            "rc.id, rc.content, ec.name) " +
            "FROM ReviewCriterial rc " +
            "JOIN EvaluationCriteria ec ON rc.evaluationCriteria.criteriaId = ec.criteriaId " +
            "WHERE rc.review.reviewId = :reviewId")
    List<ReviewCriterialDto> findCriterialByReviewId(@Param("reviewId") Long reviewId);
}
