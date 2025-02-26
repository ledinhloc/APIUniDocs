package com.android.APILogin.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentDto {
    private Long doc_id;
    private String doc_name;
    private String doc_image_url;
    private Double sell_price;
}
