package com.android.APILogin.dto.mapper;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDto toDocumentDto(Document document);
    Document toDocument(DocumentDto documentDto);
}
