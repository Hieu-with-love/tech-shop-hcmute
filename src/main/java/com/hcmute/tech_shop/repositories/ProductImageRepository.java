package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    public ProductImage findByProductName(String productName);
}
