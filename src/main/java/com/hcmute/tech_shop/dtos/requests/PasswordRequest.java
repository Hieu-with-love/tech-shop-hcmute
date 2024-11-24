package com.hcmute.tech_shop.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequest {

    private Long id;

    @NotBlank(message = "New password is required!")
    private String newPassword;

    @NotBlank(message = "Confirm password is required!")
    private String confirmPassword;
}
