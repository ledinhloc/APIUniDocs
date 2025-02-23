package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    private Integer rate;

    @Column(nullable = false)
    private LocalDateTime created_at;

    private LocalDateTime updated_at;
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doc_id")
    private Document document;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="file_id")
    private FileMedia fileMedia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="review_criterial_id")
    private  ReviewCriterial reviewCriterial;
}
