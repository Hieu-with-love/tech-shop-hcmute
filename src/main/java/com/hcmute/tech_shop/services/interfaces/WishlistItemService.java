package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.responses.WishlistItemResponse;
import com.hcmute.tech_shop.entities.Wishlist;
import com.hcmute.tech_shop.entities.WishlistItem;

import java.util.List;

public interface WishlistItemService {
    Wishlist getWishlist(Long id);
    WishlistItem getItem(Long wishlistId, Long productId);
    List<WishlistItemResponse> getItems();
    boolean insertItemIntoWishlist(Long wishlistId,Long productId);
    void removeItemFromWishlist(Long wishlistId,Long productId);
    int getItemsCount(Long wishlistId);
}
