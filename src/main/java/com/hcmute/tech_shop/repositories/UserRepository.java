package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole_Name(String roleName);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.name = 'user'")
    int countUsersByRoleUser();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.name = 'shipper'")
    int countUsersByRoleShipper();
}
