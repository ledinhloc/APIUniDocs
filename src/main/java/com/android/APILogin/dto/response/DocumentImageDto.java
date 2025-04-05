package com.android.APILogin.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImageDto implements Serializable {
    private Long imageId;
    private String docImageUrl;
}
