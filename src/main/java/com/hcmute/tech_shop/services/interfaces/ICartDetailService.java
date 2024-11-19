package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.CartDetailRequest;
import com.hcmute.tech_shop.entities.CartDetail;

import java.util.List;

public interface ICartDetailService {
    List<CartDetail> findAllByCart_Id(Long id);
    boolean create(CartDetailRequest cartDetailRequest);
    boolean update(CartDetailRequest cartDetailRequest);

    boolean delete(CartDetail cart);

    boolean deleteAll(Long cartId);

    CartDetail findByCart_IdAndProductId(Long id, Long productId);
}
