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
    Long doc_id;

    @Column(nullable = false)
    String doc_name;

    // Số trang của tài liệu
    @Column(nullable = false)
    Integer doc_page;

    @Column(nullable = false)
    String doc_image_url;

    @Column(nullable = false, scale = 2)
    Double sell_price;

    @Column(nullable = false, scale = 2)
    Double original_price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime created_at;

    // Số lượt xem tài liệu
    @Column(nullable = false)
    Integer view = 0;

    // Số lượt tải tài liệu
    @Column(nullable = false)
    Integer download = 0;

    @Enumerated(EnumType.STRING)
    DocumentType type;

    String doc_desc;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cate_id")
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

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    List<Review> reviews;
}
