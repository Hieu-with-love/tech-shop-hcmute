package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
