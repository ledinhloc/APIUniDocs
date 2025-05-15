package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.CategoryMapper;
import com.android.APILogin.dto.request.CategoryDto;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Category;
import com.android.APILogin.entity.Document;
import com.android.APILogin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        Optional<Category> categoryOtp = categoryRepository.findCategoryByCateId(id);
        if(categoryOtp.isEmpty()) {
            return null;
        }
        return categoryMapper.toCategoryDto(categoryOtp.get());
    }

    public List<CategoryDto> getCategoriesByUser(Long userId) {
        List<Category> categories = categoryRepository.findByDocumentUserId(userId);

        return categories.stream().map(c -> {
                    List<DocumentDto> docs = c.getDocuments().stream()
                            .filter(d -> d.getUser().getUserId().equals(userId))
                            .map(d -> new DocumentDto(
                                    d.getDocId(),
                                    d.getDocName(),
                                    d.getDocImageUrl()
                            ))
                            .collect(Collectors.toList());

                    return new CategoryDto(
                            c.getCateId(),
                            c.getCateName(),
                            c.getCateIcon(),
                            docs
                    );
                })
                .collect(Collectors.toList());
    }
}
