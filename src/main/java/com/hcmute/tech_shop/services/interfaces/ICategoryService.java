package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.CategoryDTO;
import com.hcmute.tech_shop.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDTO> findAll();
    Optional<CategoryDTO> findById(Long id);
    boolean existsByCategoryName(String categoryName);
    boolean addCategory(CategoryDTO categoryDTO);
    boolean updateCategory(CategoryDTO categoryDTO);
    boolean deleteCategory(Long id);
}
