package com.android.APILogin.entity;

import com.android.APILogin.enums.DiscountStatus;
import com.android.APILogin.enums.DiscountType;
import com.android.APILogin.enums.Scope;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long discountId;

    @Column(nullable = false)
    String discountName;

    @Enumerated(EnumType.STRING)
    DiscountType discountType;

    @Enumerated(EnumType.STRING)
    DiscountStatus status;

    @Enumerated(EnumType.STRING)
    Scope scope;

    // Số lần sử dụng
    @Column(nullable = false)
    @Min(1)
    Integer usageLimit;

    // Số lượng sử dụng
    @Column(nullable = false)
    @Min(0)
    Integer usedCount = 0;

    // Giá trị giảm giá
    @Column(nullable = false)
    @Min(1)
    Double discountValue ;

    private LocalDateTime startDate;
    Double maxPrice;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updateAt;
    Double minPrice;
    LocalDateTime endAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    Long scopeId;

    @JsonIgnore
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    List<OrderDiscount> orderDiscounts;
}
