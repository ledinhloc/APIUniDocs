package com.android.APILogin.entity;

import com.android.APILogin.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    private String doc_name;

    // Số trang của tài liệu
    @Column(nullable = false)
    private Integer doc_page;

    @Column(nullable = false)
    private String doc_image_url;

    @Column(nullable = false, scale = 2)
    private Double sell_price;

    @Column(nullable = false, scale = 2)
    private Double original_price;

    @Column(nullable = false)
    private LocalDateTime created_at;

    // Số lượt xem tài liệu
    @Column(nullable = false)
    private Integer view;

    // Số lượt tải tài liệu
    @Column(nullable = false)
    private Integer download;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    private String doc_desc;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "document",cascade = CascadeType.ALL)
    private List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<FileDocument> fileDocuments;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
