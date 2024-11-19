package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.CartDetail;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    @Column(name = "total_price", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0")
    private BigDecimal totalPrice;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
