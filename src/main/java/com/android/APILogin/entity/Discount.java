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
    Long discount_id;

    @Column(nullable = false)
    String discount_name;

    @Enumerated(EnumType.STRING)
    DiscountType discount_type;

    @Enumerated(EnumType.STRING)
    DiscountStatus status;

    @Enumerated(EnumType.STRING)
    Scope scope;

    // Số lần sử dụng
    @Column(nullable = false)
    @Min(1)
    Integer usage_limit;

    // Số lượng sử dụng
    @Column(nullable = false)
    @Min(0)
    Integer used_count = 0;

    // Giá trị giảm giá
    @Column(nullable = false)
    @Min(1)
    Double discount_value ;

    private LocalDateTime start_date;
    Double max_price;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime update_at;
    Double min_price;
    LocalDateTime end_at;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    Long scope_id;

    @JsonIgnore
    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    List<OrderDiscount> orderDiscounts;
}
