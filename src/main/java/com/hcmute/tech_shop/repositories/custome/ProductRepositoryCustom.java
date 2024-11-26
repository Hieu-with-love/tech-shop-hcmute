package com.hcmute.tech_shop.repositories.custome;

import com.hcmute.tech_shop.builder.ProductFilterBuilder;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryCustom {
    public static Specification<Product> filter(ProductFilterBuilder builder) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (builder.getCategoryName() != null && !builder.getCategoryName().isEmpty()) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("name"), builder.getCategoryName()));
            }

            if (builder.getBrandNames() != null && !builder.getBrandNames().isEmpty()) {
                Join<Product, Brand> brandJoin = root.join("brand");
                predicates.add(brandJoin.get("name").in(builder.getBrandNames()));
            }

            if (builder.getRams() != null && !builder.getRams().isEmpty()) {
                predicates.add(root.get("ram").in(builder.getRams()));
            }

            if (builder.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), builder.getMinPrice()));
            }
            if (builder.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), builder.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
