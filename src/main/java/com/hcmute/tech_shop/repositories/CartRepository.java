package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
