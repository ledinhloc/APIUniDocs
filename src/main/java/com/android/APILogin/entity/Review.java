package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reviewId;

    @Column(nullable = false)
    Integer rate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    //document
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="docId")
    Document document;

    //file
    @JsonIgnore
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    List<FileMedia> fileMedias;

    //review
    @JsonIgnore
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewCriterial> reviewCriterials;
}
