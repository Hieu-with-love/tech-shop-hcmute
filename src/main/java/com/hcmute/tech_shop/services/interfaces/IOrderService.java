package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.Order;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    void deleteAll();

    List<Order> findAll();

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<Order> findAll(Sort sort);
}
