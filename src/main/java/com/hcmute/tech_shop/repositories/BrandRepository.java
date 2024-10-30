package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
    Brand findByName(String name);
}
