package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
