package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.WishlistItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM WishlistItem wi WHERE wi.id = :wishlistItemId")
    void deleteWishlistItemById(Long wishlistItemId);

    void deleteAllByWishlistId(Long wishlistId);

    @Query("SELECT COUNT(wi) FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId")
    int countByWishlistId(@Param("wishlistId") Long id);
}
