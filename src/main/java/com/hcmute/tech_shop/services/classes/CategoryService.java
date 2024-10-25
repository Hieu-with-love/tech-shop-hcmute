package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.convert.CategoryConvert;
import com.hcmute.tech_shop.dtos.requests.CategoryDTO;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.repositories.CategoryRepository;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryConvert categoryConvert;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = categoryConvert.toDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}
