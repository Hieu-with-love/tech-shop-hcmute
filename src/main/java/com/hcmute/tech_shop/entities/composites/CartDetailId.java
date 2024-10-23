package com.hcmute.tech_shop.entities.composites;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartDetailId implements Serializable {
    private Long cartId;
    private Long productId;
}
