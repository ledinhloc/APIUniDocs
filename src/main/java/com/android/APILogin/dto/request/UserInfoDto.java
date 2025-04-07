package com.android.APILogin.dto.request;

import com.android.APILogin.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoDto {
    private Long userId;
    private String name;
    private String address;
    private String avatar;
    private Long totalProduct;
    private Long totalProductSale;
    private Long totalReview;
}
