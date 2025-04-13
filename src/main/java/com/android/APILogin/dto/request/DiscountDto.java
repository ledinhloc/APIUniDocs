package com.android.APILogin.dto.request;

import com.android.APILogin.enums.DiscountStatus;
import com.android.APILogin.enums.DiscountType;
import com.android.APILogin.enums.Scope;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDto {
    private Long discountId;
    private String discountName;
    private DiscountType discountType;
    private DiscountStatus status;
    private Scope scope;
    private Integer usageLimit;
    private Integer usedCount;
    private Double discountValue;
    private LocalDateTime startDate;
    private Double maxPrice;
    private Double minPrice;
    private LocalDateTime endAt;
    private Long scopeId;
}
