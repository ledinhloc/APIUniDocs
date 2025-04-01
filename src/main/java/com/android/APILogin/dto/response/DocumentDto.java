package com.android.APILogin.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentDto {
    private Long docId;
    private String docName;
    private String docImageUrl;
    private Double sellPrice;
}
