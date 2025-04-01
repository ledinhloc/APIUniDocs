package com.android.APILogin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    @NotBlank
    private String name;

    @NotNull
    @Schema(description = "File cần tải lên", type = "string", format = "binary")
    private MultipartFile file;
}
