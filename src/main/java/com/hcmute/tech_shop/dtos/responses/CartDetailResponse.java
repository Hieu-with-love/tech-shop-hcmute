package com.hcmute.tech_shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailResponse {
    private Long id;
    private String thumbnail;
    private String productName;
    private String price;
    private String totalPrice;

    private int quantity;
}
