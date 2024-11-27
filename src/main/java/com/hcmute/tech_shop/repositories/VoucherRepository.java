package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByName(String name);

    List<Voucher> findByQuantityGreaterThan(int quantityIsGreaterThan);

    List<Voucher> findByQuantityGreaterThanAndExpiredDateGreaterThan(int quantityIsGreaterThan, LocalDate expiredDateIsGreaterThan);
}
