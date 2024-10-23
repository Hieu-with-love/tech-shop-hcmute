package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.CartDetail;
import com.hcmute.tech_shop.entities.composites.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
}
