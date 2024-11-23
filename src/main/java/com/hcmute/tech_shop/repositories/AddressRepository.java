package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Username(String userUsername);
}
