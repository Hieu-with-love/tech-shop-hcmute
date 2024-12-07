package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT o.user, SUM(o.totalPrice) " +
            "FROM orders o " +
            "WHERE o.status = 'DELIVERED' " +
            "GROUP BY o.user " +
            "ORDER BY SUM(o.totalPrice) DESC")
    List<Object[]> findTop4LoyalCustomers();

}
