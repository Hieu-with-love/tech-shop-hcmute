package com.hcmute.tech_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    private boolean active;

    @NotNull(message = "Role ID is foreign key, not null")
    @JsonProperty("role_id")
    private Long roleId;

}
