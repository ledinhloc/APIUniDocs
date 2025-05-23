package com.android.APILogin.dto.request;

import com.android.APILogin.entity.FileMedia;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDto {
    private Long reviewId;
    private Integer rate;
    private LocalDateTime createdAt;
    private String content;
    private Long userId;
    private Long docId;
    private String name;
    private String avatar;
    private List<ReviewCriterialDto> criterialDtos;
    private List<FileMedia> fileMedias;
}
