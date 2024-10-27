package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.dtos.requests.ProductImageDTO;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import com.hcmute.tech_shop.repositories.ProductImageRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    public List<ProductImage> createProductImages(List<ProductImageDTO> productImagesDTO) {
        List<ProductImage> productImages = new ArrayList<>();
        for (ProductImageDTO productImageDTO : productImagesDTO) {
            Product product = productRepository
                    .findById(productImageDTO.getProductId())
                    .get();
//                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productDTO.getCategoryId()));

            ProductImage productImage = ProductImage.builder()
                    .url(productImageDTO.getUrl())
                    .product(product)
                    .build();
            productImages.add(productImage);
        }
        return null;
    }

    public Optional<ProductImage> findById(Integer integer) {
        return productImageRepository.findById(integer);
    }

    public Optional<Product> findById(Long aLong) {
        return productRepository.findById(aLong);
    }
}
