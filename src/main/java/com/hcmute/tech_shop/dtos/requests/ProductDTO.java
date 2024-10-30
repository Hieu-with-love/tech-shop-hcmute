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

    @Min(value = 1, message = "Price of product must be greater than 0")
    private BigDecimal price;

    private String cpu = "";

    private String ram = "";

    private String os = "";

    private String monitor = "";

    @Min(value = 1, message = "Weight of product must be greater than 0")
    private Double weight = 0.0;

    private String battery = "";

    private String graphicCard = "";

    private String port = "";

    private String rearCamera = "";

    private String frontCamera = "";

    @Min(value = 1, message = "Stock quantity of product must be greater than 0")
    private int stockQuantity;

    @NotEmpty(message = "Product warranty must not be empty")
    private String warranty;

    private Long categoryId;

//    @NotEmpty(message = "Product brand's name must not be empty")
    private Long brandId;

    private Boolean isEdit = false;
}
