package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"role", "addresses", "wishlist", "orders", "ratings"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")

public class User extends TrackingDate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(unique = true ,nullable = false, length = 100)
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

    @Column(name = "active",nullable = false)
    private boolean isActive;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wishlist wishlist;

    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL )
    private List<Order> orders;

}
