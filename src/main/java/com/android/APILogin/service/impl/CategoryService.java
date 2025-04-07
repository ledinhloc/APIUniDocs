package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.CategoryMapper;
import com.android.APILogin.dto.request.CategoryDto;
import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Category;
import com.android.APILogin.entity.Document;
import com.android.APILogin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

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
}
