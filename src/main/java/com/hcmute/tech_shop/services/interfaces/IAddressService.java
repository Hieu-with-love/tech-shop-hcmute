package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.Address;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAddressService {
    List<Address> findByUser_Username(String userUsername);

    List<Address> findAll();

    <S extends Address> S save(S entity);

    Optional<Address> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void deleteAll();
    void updateAddress(Map<String, String> params);
}
