package com.hcmute.tech_shop.dtos.requests;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Product name must not be empty")
    private String name;

    @NotEmpty(message = "Product description must not be empty")
    private String description;

    @NotEmpty(message = "Product price must not be empty")
    @Min(value = 1, message = "Price of product must be greater than 0")
    private BigDecimal price;

    @NotEmpty(message = "Product brand must not be empty")
    private String brand;

    @NotEmpty(message = "Product cpu must not be empty")
    private String cpu;

    @NotEmpty(message = "Product ram must not be empty")
    private String ram;

    @NotEmpty(message = "Product os must not be empty")
    private String os;

    @NotEmpty(message = "Product monitor must not be empty")
    private String monitor;

    private Double weight;

    @NotEmpty(message = "Product battery must not be empty")
    private String battery;

    private String graphicCard;

    private String port;

    private String rearCamera;

    private String frontCamera;

    @NotEmpty(message = "Product stock quantity must not be empty")
    private int stockQuantity;

    @NotEmpty(message = "Product warranty must not be empty")
    private String warranty;

    private Long categoryId;

    private Boolean isEdit = false;
}
