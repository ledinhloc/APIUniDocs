package com.android.APILogin.entity;

import com.android.APILogin.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long docId;

    @Column(nullable = false)
    String docName;

    // Số trang của tài liệu
    @Column(nullable = false)
    Integer docPage;

    @Column(nullable = false)
    String docImageUrl;

    @Column(nullable = false, scale = 2)
    Double sellPrice;

    @Column(nullable = false, scale = 2)
    Double originalPrice;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    // Số lượt xem tài liệu
    @Column(nullable = false)
    Integer view = 0;

    // Số lượt tải tài liệu
    @Column(nullable = false)
    Integer download = 0;

    @Enumerated(EnumType.STRING)
    DocumentType type;

    String docDesc;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cateId")
    Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "document",cascade = CascadeType.ALL)
    List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<FileDocument> fileDocuments;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<QuestionAnswer> questionAnswers;

    //review
    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<DocumentImage> documentImages;
}
