package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
