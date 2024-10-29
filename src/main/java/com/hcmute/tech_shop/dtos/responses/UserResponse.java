package com.hcmute.tech_shop.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    boolean active;
    LocalDate dob;
    Set<String> roles;
}
