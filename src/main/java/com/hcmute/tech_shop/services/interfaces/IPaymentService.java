package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.Payment;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {
    List<Payment> findAll();

    <S extends Payment> S save(S entity);

    Optional<Payment> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void deleteAll();

    List<Payment> findAll(Sort sort);
}
