package com.hcmute.tech_shop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String district;

    private String city;

    private String detailLocation;

    @ManyToMany(mappedBy = "addresses")
    private Set<User> users = new HashSet<>();
}
