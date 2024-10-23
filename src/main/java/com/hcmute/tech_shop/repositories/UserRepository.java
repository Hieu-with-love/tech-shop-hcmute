package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
