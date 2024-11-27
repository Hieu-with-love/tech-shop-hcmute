package com.hcmute.tech_shop.dtos.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailResponse {
    private Long id;
    private Long productId;
    private String thumbnail;
    private String productName;
    private String price;
    private String totalPriceString;
    private BigDecimal totalPrice;
    private boolean isUrlImage;

    private int quantity;
}
