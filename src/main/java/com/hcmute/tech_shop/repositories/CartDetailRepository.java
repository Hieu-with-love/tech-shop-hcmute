package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.CartDetail;
import com.hcmute.tech_shop.entities.composites.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
    List<CartDetail> findAllByCart_Id(Long id);
    Optional<CartDetail> findByCart_IdAndAndProduct_Id(Long id, Long productId);
}
