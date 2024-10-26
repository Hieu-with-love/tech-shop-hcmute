package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "URL không được để trống")
    private String url;

    @NotEmpty(message = "Product ID không được để trống")
    private Long productId;
}
