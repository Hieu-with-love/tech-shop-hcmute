package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.ProductDTO;
import com.hcmute.tech_shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductService{
    Product createProduct(ProductDTO productDTO) throws IOException;

    // has completed yet
    boolean isValidImageSuffix(String img);

    String storeFile(MultipartFile file) throws IOException;

    boolean isImage(MultipartFile file);

    Product updateProduct(Long productId, ProductDTO productDTO) throws IOException;

    void deleteProduct(Long productId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByName(String name);

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

    List<Product> findAll();

    <S extends Product> S save(S entity);

    Optional<Product> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<Product> findAll(Sort sort);

    Page<Product> findAll(Pageable pageable);

    List<ProductDTO> findByCategoryName(String categoryName);
}
