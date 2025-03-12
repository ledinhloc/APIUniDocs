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
@Table(name="review_criterial")
public class ReviewCriterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="criteria_id")
    EvaluationCriteria evaluationCriteria;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
