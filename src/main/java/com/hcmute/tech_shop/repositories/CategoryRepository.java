package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByName(String name);

    Optional<Category> findCategoryByName (String categoryName);

}
