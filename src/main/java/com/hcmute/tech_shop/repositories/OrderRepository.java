package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String userUsername);

    String user(User user);

    List<Order> getAllByShipperIdAndStatus(Long shipperId, OrderStatus status);
    List<Order> getAllByShipperId(Long shipperId);
    @Query("SELECT o " +
            "FROM orders o " +
            "WHERE YEAR(o.createdAt) = :year AND MONTH(o.createdAt) = :month AND o.shipper.id = :shipperId AND o.status = 'DELIVERED'")
    List<Order> ordersByYearAndMonthForShipper(@Param("year") int year,
                                             @Param("month") int month,
                                             @Param("shipperId") Long shipperId);
    @Query("SELECT o " +
            "FROM orders o " +
            "WHERE YEAR(o.createdAt) = :year AND MONTH(o.createdAt) = :month AND o.shipper.id = :shipperId AND o.status = 'DELIVERED'")
    List<Order> totalPriceByYearAndMonthForShipper(@Param("year") int year,
                                                       @Param("month") int month,
                                                       @Param("shipperId") Long shipperId);
}
