package com.hcmute.tech_shop.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotBlank(message = "Voucher code cannot be blank")
    private String name;

    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "1.00", message = "Value must be at least 1")
    @DecimalMax(value = "100.00", message = "Value must be at most 100")
    @Digits(integer = 10, fraction = 2, message = "Value should have up to 10 integer digits and 2 decimal places")
    private BigDecimal value;

    @NotNull(message = "Expired date cannot be null")
    @Future(message = "Expired date must be in the future")
    private LocalDate expiredDate;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}