package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.Wishlist;

public interface WishlistService {
    Wishlist getWishlistByUserId(Long id);
    Wishlist getWishlistById(Long id);
    void createWishlist(User user);
    void clearWishlist(Long id);
    boolean existsWishlist(Long userId);
}
