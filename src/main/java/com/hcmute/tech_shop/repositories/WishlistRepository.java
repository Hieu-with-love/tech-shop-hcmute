package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT COUNT(w) > 0 FROM Wishlist w WHERE w.user.id = :userId")
    boolean existsByUserId(Long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId")
    Wishlist findByUserId(Long userId);
}
