package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="reviewCriterial")
public class ReviewCriterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="criteriaId")
    EvaluationCriteria evaluationCriteria;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;
}
