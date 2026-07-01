package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.CategoryRequest;
import com.inventory.inventory_management_system.dto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    Page<CategoryResponse> getAllCategories(Pageable pageable);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}