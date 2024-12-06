package com.hcmute.tech_shop.dtos.responses;

import com.hcmute.tech_shop.entities.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageRes {
    private String url;
    private Product product;
    private boolean isUrlImage;
}
