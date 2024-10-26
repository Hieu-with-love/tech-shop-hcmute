package com.hcmute.tech_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.tech_shop.entities.Cart;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    @NotNull(message = "Email is required")
    String email;

    @NotNull(message = "Password is required")
    String password;

    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("firstname")
    String firstName;

    @JsonProperty("lastname")
    String lastName;

    boolean active;

    @NotNull(message = "Role ID is foreign key, not null")
    @JsonProperty("role_id")
    Set<String> roles;

}
