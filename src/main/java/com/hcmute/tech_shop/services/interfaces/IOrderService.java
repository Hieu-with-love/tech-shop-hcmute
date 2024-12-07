package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.OrderReponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.enums.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findByUsername(String username);

    void orderPending(Long id);

    void orderCancelled(Long id);

    void orderDelivered(Long id);

    void orderShipping(Long id);

    void createOrder(User user, BigDecimal totalPrice, Voucher voucher, Payment payment, Address address,
                     Long cartId,
                     List<CartDetailResponse> cartDetailList);

    void createOrder(User user, BigDecimal totalPrice, Payment payment, Address address,
                        Long cartId,
                        List<CartDetailResponse> cartDetailList);

    void deleteAll();

    List<Order> findAll();

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<Order> findAll(Sort sort);
    List<OrderReponse> findOrderByShipperNameAndStatus(Long shipperId, OrderStatus orderStatus);
    List<OrderReponse> findAllByShipperId(Long shipperId);

    OrderReponse findByOrderId(Long id);
    List<Order> ordersByYearAndMonthForShipper(int year, int month, Long shipperId);
    List<Order> totalPriceByYearAndMonthForShipper(int year, int month, Long shipperId);
    BigDecimal getTotalPurchaseDueForDeliveredOrders();
    int getTotalProductsSold();
    int getTotalOrder();
    int getTotalOrderForShipping();

    int getTotalOrderDelivered();

    int getTotalOrderCancelled();

    int getTotalOrderShipping();

    List<Order> getRecentlyOrders();
}
