package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends TrackingDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 255)
    private String name;

    private String description;

    @Column(name = "price")
    @Min(value = 1, message = "Price of product must be greater than 0")
    private BigDecimal price;

    private String cpu;

    private String ram;

    private String os;

    private String monitor;

    private Double weight;

    private String battery;

    @Column(name = "graphic_card")
    private String graphicCard;

    private String port;

    @Column(name = "rear_camera")
    private String rearCamera;

    @Column(name = "front_camera")
    private String frontCamera;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "warranty", nullable = false)
    private String warranty;

    private String thumbnail;

    // a product must have category_id, and load Category once called, and obligated foreign key
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // a product must have brand_id, and load all Brand, and obligated foreign key
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
}
