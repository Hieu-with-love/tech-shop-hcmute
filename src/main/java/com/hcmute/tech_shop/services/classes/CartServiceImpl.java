package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.repositories.CartRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
