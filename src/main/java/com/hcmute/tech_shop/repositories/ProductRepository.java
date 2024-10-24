package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
