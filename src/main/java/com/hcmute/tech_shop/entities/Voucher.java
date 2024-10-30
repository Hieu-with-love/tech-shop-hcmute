package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vouchers")
public class Voucher extends TrackingDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(nullable = false)
    private int quantity;

    @OneToMany(mappedBy = "voucher")
    private List<Order> orders;
}
