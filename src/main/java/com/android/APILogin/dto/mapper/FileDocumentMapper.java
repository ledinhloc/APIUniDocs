package com.android.APILogin.dto.mapper;

import com.android.APILogin.dto.response.FileDtoResponse;
import com.android.APILogin.entity.FileDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileDocumentMapper {
    FileDtoResponse toDto(FileDocument fileDocument);
    FileDocument toEntity(FileDtoResponse fileDtoResponse);
}
