package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carts")
@ToString(exclude = "cartDetails")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price", nullable = false, columnDefinition = "DECIMAL(19, 2) DEFAULT 0")
    private BigDecimal totalPrice;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;

}
