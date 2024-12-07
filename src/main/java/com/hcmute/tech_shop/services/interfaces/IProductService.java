package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProductService {
    List<ProductResponse> findByNameContaining(String name);

    void decreaseStockQuantity(Long productId, int quantity);

    void init();
    List<ProductResponse> getAllProducts(List<Product> products);
    ProductResponse getProductResponse(Long productId);

    Page<ProductResponse> filterProducts(Map<String, Object> params, Pageable pageable);

    boolean deleteImage(String filename);

    boolean createProduct(ProductRequest productRequest, MultipartFile file) throws IOException;

    Product updateProduct(Long productId, ProductRequest productRequest) throws IOException;

    Product updateProduct(Long productId, ProductRequest productRequest, MultipartFile file) throws IOException;

    void deleteProduct(Long productId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
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

    List<Product> findAll();

    <S extends Product> S save(S entity);

    Optional<Product> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<ProductRequest> findByCategoryName(String categoryName);
    Page<Product> getAllProducts(int pageNumber, int pageSize);
    Page<Product> getAllSortedProducts(int pageNumber, int pageSize, Sort sort);
    int getTotalStockQuantity();
}
