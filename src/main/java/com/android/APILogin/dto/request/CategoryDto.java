package com.android.APILogin.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    private Long cate_id;
    private String cate_name;
    private String cate_desc;
    private String cate_icon;
}
