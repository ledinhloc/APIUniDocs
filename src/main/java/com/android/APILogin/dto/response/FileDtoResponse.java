package com.android.APILogin.dto.response;

import com.android.APILogin.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDtoResponse {
    private Long fileId;
    private String fileUrl;
    private FileType fileType;
}
