package com.android.APILogin.dto.mapper;

import com.android.APILogin.dto.request.CategoryDto;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Category;
import com.android.APILogin.entity.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    Category toCategory(CategoryDto categoryDto);
}
