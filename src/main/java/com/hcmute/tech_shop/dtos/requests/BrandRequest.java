package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name="brand_img", length = 500)
    private String brandImg;
}
