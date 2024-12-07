package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class RatingRequest {
    @Column(nullable = false, length = 65535)
    private String content;

    @Column(nullable = false)
    private int star;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Long orderId;
}
