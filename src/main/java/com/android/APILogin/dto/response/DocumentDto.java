package com.android.APILogin.dto.response;

import com.android.APILogin.entity.Document;
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
    private Long docId;//
    private String docName;
    private String docImageUrl;
    private Double sellPrice;
    private Double originalPrice;
    private Integer docPage;
    private Integer view;//
    private Integer download;//
    private String docDesc;
    private DocumentType type;
    private LocalDateTime createdAt;//
    private Integer maxQuantity;//so luong còn
    private Long userId;
    private Long cateId;
    private Long totalSold;//tong luot ban
    private Double avgRate;//
    private Long totalReview;//


    public DocumentDto(Long docId, String docName, String docImageUrl, Double sellPrice) {
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
        this.sellPrice = sellPrice;
    }

    public DocumentDto(Long docId, Long userId, Long cateId){
        this.docId = docId;
        this.userId = userId;
        this.cateId = cateId;
    }

    public DocumentDto(Long docId, String docName, String docImageUrl){
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
    }

    public DocumentDto(Long docId, String docName, String docImageUrl, Double sellPrice, Long totalSold) {
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
        this.sellPrice = sellPrice;
        this.totalSold = totalSold;
    }

    public DocumentDto(Long docId, String docName, String docImageUrl, Double sellPrice, Long totalSold, Double avgRate) {
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
        this.sellPrice = sellPrice;
        this.totalSold = totalSold;
        this.avgRate = avgRate;
    }

    public DocumentDto(Long docId, String docName, String docImageUrl, Double sellPrice,Long userId, Long totalSold, Double avgRate) {
        this.docId = docId;
        this.docName = docName;
        this.docImageUrl = docImageUrl;
        this.sellPrice = sellPrice;
        this.userId = userId;
        this.totalSold = totalSold;
        this.avgRate = avgRate;
    }
}
