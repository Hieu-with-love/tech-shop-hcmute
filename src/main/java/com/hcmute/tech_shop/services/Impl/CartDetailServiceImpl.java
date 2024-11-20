package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.CartDetailRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.CartDetail;
import com.hcmute.tech_shop.entities.composites.CartDetailId;
import com.hcmute.tech_shop.repositories.CartDetailRepository;
import com.hcmute.tech_shop.repositories.CartRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartDetailServiceImpl implements ICartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartDetail> findAllByCart_Id(Long id) {
        return cartDetailRepository.findAllByCart_Id(id);
    }

    @Override
    public List<CartDetailResponse> getAllItems(List<CartDetail> cartDetails) {
        return cartDetails.stream().map(item ->{
            BigDecimal totalPrice = item.getTotalPrice();
            String totalPriceStr = Constant.formatter.format(totalPrice);

            return CartDetailResponse.builder()
                    .id(item.getId().getCartId())
                    .thumbnail(item.getProduct().getThumbnail())
                    .productName(item.getProduct().getName())
                    .price(Constant.formatter.format(item.getProduct().getPrice()))
                    .totalPrice(totalPriceStr)
                    .quantity(item.getQuantity())
                    .build();
        }).toList();
    }

    @Override
    public boolean create(CartDetailRequest cartDetailRequest) {
        try {
            if (cartDetailRequest.getProduct() != null) {
                CartDetail oldCart = cartDetailRepository.findByCart_IdAndAndProduct_Id(cartDetailRequest.getCart().getId(), cartDetailRequest.getProduct().getId()).orElse(null);
                Cart cart = cartService.findById(cartDetailRequest.getCart().getId());
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(cartDetailRequest.getProduct());
                cartDetail.setQuantity(cartDetailRequest.getQuantity());
                cartDetail.setId(new CartDetailId(cartDetailRequest.getCart().getId(), cartDetailRequest.getProduct().getId()));
                cartDetail.setTotalPrice(cartDetailRequest.getTotalPrice());
                cartDetailRepository.save(cartDetail);
                if (oldCart != null) {
                    cart.setTotalPrice(cart.getTotalPrice().subtract(oldCart.getTotalPrice()).add(cartDetail.getTotalPrice()));
                }
                else {
                    cart.setTotalPrice(cart.getTotalPrice().add(cartDetail.getTotalPrice()));
                }
                cartRepository.save(cart);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(CartDetailRequest cartDetailRequest) {
        try {
            CartDetail oldCartDetail = cartDetailRepository.findByCart_IdAndAndProduct_Id(cartDetailRequest.getCart().getId(), cartDetailRequest.getProduct().getId()).orElse(null);
            if (oldCartDetail != null) {
                Cart oldCart = cartService.findById(cartDetailRequest.getCart().getId());
                BigDecimal totalPrice = oldCart.getTotalPrice().subtract(oldCartDetail.getTotalPrice()).add(cartDetailRequest.getTotalPrice());

                oldCartDetail.setQuantity(cartDetailRequest.getQuantity());
                oldCartDetail.setTotalPrice(cartDetailRequest.getTotalPrice());
                oldCart.setTotalPrice(totalPrice);

                cartDetailRepository.save(oldCartDetail);
                cartRepository.save(oldCart);

                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(CartDetail cartDetail) {
        try {
            Cart cart = cartService.findById(cartDetail.getCart().getId());
            if(cartDetail != null) {
                cart.setTotalPrice(cart.getTotalPrice().subtract(cartDetail.getTotalPrice()));
                cartDetailRepository.delete(cartDetail);
                cartRepository.save(cart);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll(Long cartId){
        try {
            List<CartDetail> cartDetails = cartDetailRepository.findAllByCart_Id(cartId);
            for (CartDetail cartDetail : cartDetails) {
                this.delete(cartDetail);
            }
            Cart cart = cartService.findById(cartId);
            cartRepository.save(cart);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CartDetail findByCart_IdAndProductId(Long id, Long productId) {
        return cartDetailRepository.findByCart_IdAndAndProduct_Id(id,productId).orElse(null);
    }
}
