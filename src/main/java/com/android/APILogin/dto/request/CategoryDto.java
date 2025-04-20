package com.android.APILogin.dto.request;

import com.android.APILogin.dto.response.DocumentDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    private List<DocumentDto> docs;

    public CategoryDto(Long cateId, String cateName, String cateIcon, List<DocumentDto> docs) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.cateIcon = cateIcon;
        this.docs = docs;
    }
}
