package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByName(String name);
}
