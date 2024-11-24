package com.hcmute.tech_shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private String thumbnail;
    private String title;
    private String unitPrice;
    private boolean status;
    private Long productId;
}
