package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByName(String name);
}
