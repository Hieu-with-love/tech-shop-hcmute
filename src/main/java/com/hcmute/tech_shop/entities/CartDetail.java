package com.hcmute.tech_shop.entities;

import com.hcmute.tech_shop.entities.composites.CartDetailId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_details")
public class CartDetail {
    @EmbeddedId
    private CartDetailId id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false, columnDefinition = "DECIMAL(19, 2) DEFAULT 0")
    private BigDecimal totalPrice;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
