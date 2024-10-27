package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends TrackingDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "firstname",nullable = false, length = 255)
    private String firstName;

    @Column(name = "lastname", nullable = false, length = 255)
    private String lastName;

    @Column(name = "day_of_birth")
    private LocalDate dateOfBirth;

    private String gender;

    // This tells JPA to treat the roles field as a collection of basic values.
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roles;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

}
