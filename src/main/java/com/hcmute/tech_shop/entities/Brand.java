package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "brands")
@ToString(exclude = "products") // Không bao gồm products trong toString()
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name="brand_img", length = 500)
    private String brandImg;

    @Column(name = "active",nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
