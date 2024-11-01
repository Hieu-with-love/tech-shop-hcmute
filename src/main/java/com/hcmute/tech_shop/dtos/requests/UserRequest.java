package com.hcmute.tech_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.tech_shop.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotNull(message = "Username is required")
    String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotNull(message = "Password is required")
    String password;

    @NotNull(message = "Password is required")
    @JsonProperty("confirm_password")
    String confirmPassword;

    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("firstname")
    String firstName;

    @JsonProperty("lastname")
    String lastName;

    String gender;

    LocalDate dob;

    boolean active;

    Long roleId;

}
