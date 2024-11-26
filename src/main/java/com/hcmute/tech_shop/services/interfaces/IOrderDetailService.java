package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.OrderDetail;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    List<OrderDetail> findByProductId(Long productId);

    List<OrderDetail> findAll();

    <S extends OrderDetail> S save(S entity);

    Optional<OrderDetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(OrderDetail entity);

    void deleteAll();

    List<OrderDetail> findAll(Sort sort);
}
