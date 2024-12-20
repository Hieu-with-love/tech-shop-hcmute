package com.hcmute.tech_shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String price;
    private String oldPrice;
    private String thumbnail;
    private String description;
    private int stockQuantity;
    private boolean isUrlImage;
}
