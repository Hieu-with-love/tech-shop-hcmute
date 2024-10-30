package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.dtos.requests.ProductDTO;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.repositories.BrandRepository;
import com.hcmute.tech_shop.repositories.CategoryRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean createProduct(ProductRequest productRequest) throws IOException {
        try {
            System.out.println(productRequest.getCategoryId());
            Optional<Category> category = categoryRepository.findById(productRequest.getCategoryId());
            Category categoryExisting = category.get();
            Brand brand = brandRepository.findById(productRequest.getBrandId()).get();
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .cpu(productRequest.getCpu())
                    .ram(productRequest.getRam())
                    .os(productRequest.getOs())
                    .monitor(productRequest.getMonitor())
                    .battery(productRequest.getBattery())
                    .graphicCard(productRequest.getGraphicCard())
                    .port(productRequest.getPort())
                    .rearCamera(productRequest.getRearCamera())
                    .frontCamera(productRequest.getFrontCamera())
                    .stockQuantity(productRequest.getStockQuantity())
                    .warranty(productRequest.getWarranty())
                    .weight(productRequest.getWeight())
                    .category(categoryExisting)
                    .brand(brand)
                    .build();
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) throws IOException {
        Category categoryExisting = categoryRepository
                .findById(productRequest.getCategoryId())
                .get();
        Brand brandExisting = brandRepository
                .findById(productRequest.getBrandId())
                .get();
//                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productRequest.getCategoryId()));

        // get product old by id
        Product existingProduct = productRepository.findById(productId).get();
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCpu(productRequest.getCpu());
        existingProduct.setRam(productRequest.getRam());
        existingProduct.setOs(productRequest.getOs());
        existingProduct.setMonitor(productRequest.getMonitor());
        existingProduct.setBattery(productRequest.getBattery());
        existingProduct.setGraphicCard(productRequest.getGraphicCard());
        existingProduct.setPort(productRequest.getPort());
        existingProduct.setRearCamera(productRequest.getRearCamera());
        existingProduct.setFrontCamera(productRequest.getFrontCamera());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setWarranty(productRequest.getWarranty());
        existingProduct.setWeight(productRequest.getWeight());
        existingProduct.setCategory(categoryExisting);
        existingProduct.setBrand(brandExisting);
        // create if chua ton tai, update if ton tai
        return productRepository.save(existingProduct);
    }

//    public void createProductImage(Long productId, ProductImageDTO productImageDTO) {
//        Product existingProduct = getProductById(productId);
//
//        ProductImage newProductImage = ProductImage.builder()
//                .product(existingProduct)
//                .imageUrl(productImageDTO.getImageUrl())
//                .build();
//
//        productImageRepository.save(newProductImage);
//    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = findById(productId).get();
        productRepository.delete(product);
    }

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Query("SELECT p FROM Product p WHERE p.cpu LIKE %?1%")
    @Override
    public List<Product> findByCpu(String cpu) {
        return productRepository.findByCpu(cpu);
    }

    @Query("SELECT p FROM Product p WHERE p.ram LIKE %?1%")
    @Override
    public List<Product> findByRam(String ram) {
        return productRepository.findByRam(ram);
    }

    @Query("SELECT p FROM Product p WHERE p.os LIKE %?1%")
    @Override
    public List<Product> findByOs(String os) {
        return productRepository.findByOs(os);
    }

    @Query("SELECT p FROM Product p WHERE p.monitor LIKE %?1%")
    @Override
    public List<Product> findByMonitor(String monitor) {
        return productRepository.findByMonitor(monitor);
    }

    @Query("SELECT p FROM Product p WHERE p.weight = ?1")
    @Override
    public List<Product> findByWeight(Double weight) {
        return productRepository.findByWeight(weight);
    }

    @Query("SELECT p FROM Product p WHERE p.battery LIKE %?1%")
    @Override
    public List<Product> findByBattery(String battery) {
        return productRepository.findByBattery(battery);
    }

    @Query("SELECT p FROM Product p WHERE p.graphicCard LIKE %?1%")
    @Override
    public List<Product> findByGraphicCard(String graphicCard) {
        return productRepository.findByGraphicCard(graphicCard);
    }

    @Query("SELECT p FROM Product p WHERE p.port LIKE %?1%")
    @Override
    public List<Product> findByPort(String port) {
        return productRepository.findByPort(port);
    }

    @Query("SELECT p FROM Product p WHERE p.rearCamera LIKE %?1%")
    @Override
    public List<Product> findByRearCamera(String rearCamera) {
        return productRepository.findByRearCamera(rearCamera);
    }

    @Query("SELECT p FROM Product p WHERE p.frontCamera LIKE %?1%")
    @Override
    public List<Product> findByFrontCamera(String frontCamera) {
        return productRepository.findByFrontCamera(frontCamera);
    }

    @Query("SELECT p FROM Product p WHERE p.stockQuantity = ?1")
    @Override
    public List<Product> findByStockQuantity(int stockQuantity) {
        return productRepository.findByStockQuantity(stockQuantity);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public Optional<Product> findById(Long aLong) {
        return productRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return productRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<CategoryRequest> findByCategoryName(String categoryName) {
        List<Category> productDTOList = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryName(categoryName);
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }
}
