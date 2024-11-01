package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductImageService {
    boolean createProductImages(Long productId, MultipartFile file) throws IOException;

    List<ProductImage> findByProductId(Long productId);

    void deleteById(Integer integer);

    Optional<ProductImage> findById(Integer integer);

    Optional<Product> findById(Long aLong);
}
