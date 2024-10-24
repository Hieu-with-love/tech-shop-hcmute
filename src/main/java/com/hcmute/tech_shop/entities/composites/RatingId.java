package com.hcmute.tech_shop.entities.composites;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {
    private Long userId;
    private Long productId;
}
