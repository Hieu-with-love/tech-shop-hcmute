package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.CartRequest;
import com.hcmute.tech_shop.dtos.responses.CartResponse;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.repositories.CartRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.utils.Constant;
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
    public CartResponse getCartResponse(Cart cart) {
        return CartResponse.builder()
                .totalPrice(Constant.formatter.format(cart.getTotalPrice()))
                .build();
    }

    @Override
    public Cart createCart(Cart cart) {
        try {
            cartRepository.save(cart);
            return cart;
        }
        catch (Exception e) {
            throw new RuntimeException("Cart creation failed");
        }
    }

    @Override
    public Cart findByCustomerId(Long customerId) {
        return cartRepository.findByUserId(customerId).orElse(null);
    }

    @Override
    public Cart findById(Long cartId){
        return cartRepository.findById(cartId).orElse(null);
    }

}
