package com.android.APILogin.dto.mapper;

import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.dto.response.DocumentImageDto;
import com.android.APILogin.entity.Document;
import com.android.APILogin.entity.DocumentImage;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DocumentImageMapper {
    DocumentImageDto toDocumentImageDto(DocumentImage documentImage);
    DocumentImage toDocumentImage(DocumentImageDto documentImageDto);
}
