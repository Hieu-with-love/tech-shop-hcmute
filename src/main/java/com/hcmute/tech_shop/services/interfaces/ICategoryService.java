package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryRequest> findAll();
    Optional<CategoryRequest> findById(Long id);
    boolean existsByCategoryName(String categoryName);
    boolean addCategory(CategoryRequest categoryRequest);
    boolean updateCategory(CategoryRequest categoryRequest);
    boolean deleteCategory(Long id);
}
