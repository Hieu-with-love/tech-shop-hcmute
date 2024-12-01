package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String userUsername);

    String user(User user);
}
