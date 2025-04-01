package com.android.APILogin.dto.mapper;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mapping(source = "docId", target = "docId")
    @Mapping(source = "docName", target = "docName")
    @Mapping(source = "docImageUrl", target = "docImageUrl")
    @Mapping(source = "sellPrice", target = "sellPrice")
    DocumentDto toDocumentDto(Document document);
    Document toDocument(DocumentDto documentDto);
}
