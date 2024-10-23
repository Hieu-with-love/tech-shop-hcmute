package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
