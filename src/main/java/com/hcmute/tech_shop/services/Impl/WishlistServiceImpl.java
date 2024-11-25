package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.Wishlist;
import com.hcmute.tech_shop.repositories.WishlistItemRepository;
import com.hcmute.tech_shop.repositories.WishlistRepository;
import com.hcmute.tech_shop.services.interfaces.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional
    public Wishlist getWishlistByUserId(Long id) {
        return wishlistRepository.findByUserId(id);
    }

    @Override
    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(new Wishlist());
    }

    @Override
    public boolean existsWishlist(Long userId) {
        return wishlistRepository.existsByUserId(userId);
    }

    @Override
    @Transactional
    public void createWishlist(User user) {
        if (!existsWishlist(user.getId())) {
            Wishlist wishlist = Wishlist.builder()
                    .user(user)
                    .build();
            wishlistRepository.save(wishlist);
        }
    }

    @Override
    @Transactional
    public void clearWishlist(Long id) {
        Wishlist wishlist = this.getWishlistById(id);
        wishlist.getItems().clear();
        wishlistItemRepository.deleteWishlistItemByWishlistId(id);
    }


}
