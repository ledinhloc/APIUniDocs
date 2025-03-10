package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long review_id;

    @Column(nullable = false)
    Integer rate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updated_at;

    String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doc_id")
    Document document;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="file_id")
    FileMedia fileMedia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="review_criterial_id")
    ReviewCriterial reviewCriterial;
}
