package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.ProductDTO;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) throws IOException {
        Category categoryExisting = categoryRepository
                .findById(productDTO.getCategoryId())
                .get();
//                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productDTO.getCategoryId()));

        Product product = Product.builder()
                .battery(productDTO.getBattery())
                .cpu(productDTO.getCpu())
                .description(productDTO.getDescription())
                .frontCamera(productDTO.getFrontCamera())
                .graphicCard(productDTO.getGraphicCard())
                .monitor(productDTO.getMonitor())
                .name(productDTO.getName())
                .os(productDTO.getOs())
                .port(productDTO.getPort())
                .price(productDTO.getPrice())
                .ram(productDTO.getRam())
                .rearCamera(productDTO.getRearCamera())
                .stockQuantity(productDTO.getStockQuantity())
                .warranty(productDTO.getWarranty())
                .weight(productDTO.getWeight())
                .category(categoryExisting)
                .build();
        return productRepository.save(product);
    }

    // has completed yet
    @Override
    public boolean isValidImageSuffix(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (file.getSize() == 0)
            return "File is empty";
        if (file.getSize() > 10 * 1024 * 1024) {
            return "File is too large. Maximum size is 10MB";
        }
        if (!isImage(file)) {
            return "File is not an image";
        }
        // get file name
        String fileName = file.getOriginalFilename();
        // generate code random base on UUID
        String uniqueFileName = UUID.randomUUID().toString() + "_" + LocalDate.now() + "_" + fileName;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // check and create if haven't existed
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) throws IOException {
        Category categoryExisting = categoryRepository
                .findById(productDTO.getCategoryId())
                .get();
//                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productDTO.getCategoryId()));

        // get product old by id
        Product existingProduct = productRepository.findById(productId).get();

        // handle delete old image if has image uploaded
//        if (!productDTO.getFileImage().isEmpty()) {
//            if (!isValidImageSuffix(Objects.requireNonNull(productDTO.getFileImage().getOriginalFilename()))) {
//                throw new NotFoundException("File is not an image");
//            }
//            String uploadDir = "uploads/";
//            java.nio.file.Path oldImagePath = Paths.get(uploadDir + existingProduct.getThumbnail());
//            try {
//                if (!oldImagePath.getFileName().toString().equals("default-product.jpg")) {
//                    Files.delete(oldImagePath);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("Cannot delete old image." + e.getMessage());
//            }
//            // store file be able to exception
//            String newThumbnail = storeFile(productDTO.getFileImage());
//            existingProduct.setThumbnail(newThumbnail);
//        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setRam(productDTO.getRam());
        existingProduct.setBattery(productDTO.getBattery());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setMonitor(productDTO.getMonitor());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setCategory(categoryExisting);

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
        if (product == null)
//            throw new NotFoundException("Product with id = " + productId + " not found");
//        java.nio.file.Path oldImagePath = Paths.get("uploads/", product.getThumbnail());
//        try {
//            if (!oldImagePath.getFileName().toString().equals("default-product.jpg")) {
//                Files.delete(oldImagePath);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Cannot delete old image. \n" + e.getMessage());
//        }
            productRepository.deleteById(productId);
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
    public List<ProductDTO> findByCategoryName(String categoryName) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryName(categoryName);
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }
}
