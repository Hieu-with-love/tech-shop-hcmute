package com.hcmute.tech_shop.entities.composites;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RatingId implements Serializable {
    private Long userId;
    private Long productId;
    private Long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        RatingId that = (RatingId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId) && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId, orderId);
    }
 }
