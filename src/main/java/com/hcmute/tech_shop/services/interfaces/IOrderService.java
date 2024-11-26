package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.Payment;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.Voucher;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    void createOrder(User user, BigDecimal totalPrice, Voucher voucher, Payment payment,
                        Long cartId,
                        List<CartDetailResponse> cartDetailList);

    void createOrder(User user, BigDecimal totalPrice, Payment payment,
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
}
