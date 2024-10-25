package com.hcmute.tech_shop.convert;

import com.hcmute.tech_shop.dtos.requests.CategoryDTO;
import com.hcmute.tech_shop.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConvert {
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO toDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}