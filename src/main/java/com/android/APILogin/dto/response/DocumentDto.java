package com.android.APILogin.dto.response;

import com.android.APILogin.enums.DocumentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    private Double originalPrice;
    private Integer docPage;
    private Integer view;
    private Integer download;
    private String docDesc;
    private DocumentType type;
    private LocalDateTime createdAt;
    private Integer maxQuantity;
    private Long totalSold;
    private Double avgRate;

    public DocumentDto(Long docId, String docName, String docImageUrl, Double sellPrice) {
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
        this.sellPrice = sellPrice;
    }
}
