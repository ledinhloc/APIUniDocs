package com.android.APILogin.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
    private Long cartId;
    private Integer quantity;
    private Long userId;
    private Long docId;
    private String docName;
    private Double sellPrice;
    private String docImageUrl;
}
