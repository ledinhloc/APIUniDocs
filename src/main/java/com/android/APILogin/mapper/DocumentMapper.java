package com.android.APILogin.mapper;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDto toDocumentDto(Document document);
    Document toDocument(DocumentDto documentDto);
}
