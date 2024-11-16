package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.convert.CategoryConvert;
import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.repositories.CategoryRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
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

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return Optional.ofNullable(category);
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName).orElse(null);
    }

    @Override
    public boolean addCategory(CategoryRequest categoryRequest) {
        try {
            if(this.findByCategoryName(categoryRequest.getName()) != null) {
                return false;
            }
            categoryRequest.setActive(true);
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
    public boolean updateCategory(CategoryRequest categoryRequest, Long id) {
        try {
            Category category1 = new Category();
            category1.setId(id);
            category1.setName(categoryRequest.getName());
            category1.setDescription(categoryRequest.getDescription());
            category1.setActive(categoryRequest.isActive());
            categoryRepository.save(category1);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            if(!productRepository.findByCategoryName(category.getName()).isEmpty()){
                category.setActive(false);
                categoryRepository.save(category);
            }
            else{
                categoryRepository.deleteById(id);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
