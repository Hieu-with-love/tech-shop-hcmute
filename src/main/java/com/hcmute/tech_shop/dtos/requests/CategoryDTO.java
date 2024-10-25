package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;

    @NotNull(message = "Category name is required")
    private String name;
}
