package com.android.APILogin.entity;

import com.android.APILogin.enums.DiscountStatus;
import com.android.APILogin.enums.DiscountType;
import com.android.APILogin.enums.Scope;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Long discount_id;

    @Column(nullable = false)
    private String discount_name;

    @Enumerated(EnumType.STRING)
    private DiscountType discount_type;

    @Enumerated(EnumType.STRING)
    private DiscountStatus status;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    // Số lần sử dụng
    @Column(nullable = false)
    @Min(1)
    private Integer usage_limit;

    // Số lượng sử dụng
    @Column(nullable = false)
    @Min(0)
    private Integer used_count;

    // Giá trị giảm giá
    @Column(nullable = false)
    @Min(1)
    private Double discount_value;

    private LocalDateTime start_date;
    private Double max_price;
    private LocalDateTime update_at;
    private Double min_price;
    private LocalDateTime end_at;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long scope_id;

    @JsonIgnore
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    private List<OrderDiscount> orderDiscounts;
}
