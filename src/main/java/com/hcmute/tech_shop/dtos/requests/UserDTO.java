package com.hcmute.tech_shop.dtos.requests;

import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String email;

    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private boolean active;

    private Role role;

    private Cart cart;
}
