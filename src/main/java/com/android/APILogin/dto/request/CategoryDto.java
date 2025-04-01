package com.android.APILogin.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    private Long cateId;
    private String cateName;
    private String cateDesc;
    private String cateIcon;
}
