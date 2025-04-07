package com.android.APILogin.controller;

import com.android.APILogin.dto.request.CategoryDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseData<List<CategoryDto>> getAllCategories() {
        return new ResponseData<>(HttpStatus.OK.value(), "list category", categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseData<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if(categoryDto == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "category not found", categoryDto);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "category found", categoryDto);
        }
    }
}
