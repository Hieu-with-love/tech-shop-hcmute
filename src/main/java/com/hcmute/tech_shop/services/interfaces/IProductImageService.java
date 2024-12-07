package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.responses.ProductImageRes;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductImageService {
    void deleteAllByProduct_Id(Long productId);

    boolean createProductImages(Long productId, MultipartFile file) throws IOException;

    List<ProductImage> findByProductId(Long productId);
    List<ProductImageRes> getProductImages(Long productId);

    void deleteById(Integer integer);

    Optional<ProductImage> findById(Integer integer);

    Optional<Product> findById(Long aLong);
}
