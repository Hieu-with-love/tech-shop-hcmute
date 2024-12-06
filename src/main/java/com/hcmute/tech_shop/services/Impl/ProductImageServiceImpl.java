package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.responses.ProductImageRes;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import com.hcmute.tech_shop.repositories.ProductImageRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductImageServiceImpl implements IProductImageService {
    private final Path root = Paths.get("./uploads");

    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean createProductImages(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElse(null);
        try {
            String thumbnail = "";
            if (file == null) {
                thumbnail = "/uploads/default-product.jpg";
            } else {
                if (!isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                    throw new BadRequestException("Image is not valid");
                }
                thumbnail = saveImage(file);
            }
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .url(thumbnail)
                    .build();
            productImageRepository.save(productImage);
            return true;
        } catch (IOException ioe) {
            throw new IOException("Cannot create product" + ioe.getMessage());
        }
    }

    public String saveImage(MultipartFile file) {
        try {
            // get file name
            String fileName = file.getOriginalFilename();
            // generate code random base on UUID
            String uniqueFileName = UUID.randomUUID().toString() + "_" + LocalDate.now() + "_" + fileName;
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Filename already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean isValidSuffixImage(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    @Override
    public List<ProductImageRes> getProductImages(Long productId) {
        List<ProductImage> productImages = this.findByProductId(productId);
        return productImages.stream()
                .map(productImage -> ProductImageRes.builder()
                        .url(productImage.getUrl())
                        .product(productImage.getProduct())
                        .isUrlImage(productImage.getProduct().getThumbnail().contains("https"))
                        .build()).toList();
    }

    @Override
    public void deleteById(Integer integer) {
        productImageRepository.deleteById(integer);
    }

    @Override
    public Optional<ProductImage> findById(Integer integer) {
        return productImageRepository.findById(integer);
    }

    @Override
    public Optional<Product> findById(Long aLong) {
        return productRepository.findById(aLong);
    }
}
