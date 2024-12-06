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
import java.util.stream.Collectors;

public class ProductRepositoryCustom {
    public static Specification<Product> filter(ProductFilterBuilder builder) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (builder.getName() != null && !builder.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + builder.getName().toLowerCase() + "%"));
            }

            if (builder.getCategoryName() != null && !builder.getCategoryName().isEmpty()) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("name"), builder.getCategoryName()));
            }

            if (builder.getBrandNames() != null && !builder.getBrandNames().isEmpty()) {
                // Filter out empty strings from the list before using it in the query
                List<String> filteredBrandNames = builder.getBrandNames().stream()
                        .filter(name -> !name.trim().isEmpty())
                        .collect(Collectors.toList());

                if (!filteredBrandNames.isEmpty()) {
                    Join<Product, Brand> brandJoin = root.join("brand");
                    predicates.add(brandJoin.get("name").in(filteredBrandNames));
                }
            }

            if (builder.getRams() != null && !builder.getRams().isEmpty()) {
                // Filter out empty strings from the list before using it in the query
                List<String> filteredRams = builder.getRams().stream()
                        .filter(ram -> !ram.trim().isEmpty())
                        .collect(Collectors.toList());

                if (!filteredRams.isEmpty()) {
                    predicates.add(root.get("ram").in(filteredRams));
                }
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
