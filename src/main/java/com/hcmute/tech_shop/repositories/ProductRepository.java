package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByNameContaining(String name);

    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.cpu LIKE %?1%")
    List<Product> findByCpu(String cpu);

    @Query("SELECT p FROM Product p WHERE p.ram LIKE %?1%")
    List<Product> findByRam(String ram);

    @Query("SELECT p FROM Product p WHERE p.os LIKE %?1%")
    List<Product> findByOs(String os);

    @Query("SELECT p FROM Product p WHERE p.monitor LIKE %?1%")
    List<Product> findByMonitor(String monitor);

    @Query("SELECT p FROM Product p WHERE p.weight = ?1")
    List<Product> findByWeight(Double weight);

    @Query("SELECT p FROM Product p WHERE p.battery LIKE %?1%")
    List<Product> findByBattery(String battery);

    @Query("SELECT p FROM Product p WHERE p.graphicCard LIKE %?1%")
    List<Product> findByGraphicCard(String graphicCard);

    @Query("SELECT p FROM Product p WHERE p.port LIKE %?1%")
    List<Product> findByPort(String port);

    @Query("SELECT p FROM Product p WHERE p.rearCamera LIKE %?1%")
    List<Product> findByRearCamera(String rearCamera);

    @Query("SELECT p FROM Product p WHERE p.frontCamera LIKE %?1%")
    List<Product> findByFrontCamera(String frontCamera);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity = ?1")
    List<Product> findByStockQuantity(int stockQuantity);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrandName(String brandName);

    // Paging and Sorting methods
    Page<Product> findAll(Pageable pageable);

    List<Product> findTop4ByOrderByCreatedAtDesc();

}
