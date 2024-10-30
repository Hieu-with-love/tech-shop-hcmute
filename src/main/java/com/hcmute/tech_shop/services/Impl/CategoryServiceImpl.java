package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.convert.CategoryConvert;
import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.repositories.CategoryRepository;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryConvert categoryConvert;

    @Override
    public List<CategoryRequest> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryRequest> categoryRequestList = new ArrayList<>();
        for (Category category : categories) {
            CategoryRequest categoryRequest = categoryConvert.toDTO(category);
            categoryRequestList.add(categoryRequest);
        }
        return categoryRequestList;
    }

    @Override
    public Optional<CategoryRequest> findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return Optional.ofNullable(categoryConvert.toDTO(category));
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        if(categoryRepository.findCategoriesByName(categoryName).isEmpty())
            return false;
        return true;
    }

    @Override
    public boolean addCategory(CategoryRequest categoryRequest) {
        try {
            if(this.existsByCategoryName(categoryRequest.getName())) {
                return false;
            }
            Category category = categoryConvert.toEntity(categoryRequest);
            categoryRepository.save(category);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCategory(CategoryRequest categoryRequest) {
        try {
            Category category = categoryConvert.toEntity(categoryRequest);
            categoryRepository.save(category);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
