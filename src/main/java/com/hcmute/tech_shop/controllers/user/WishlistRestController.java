package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.entities.Wishlist;
import com.hcmute.tech_shop.entities.WishlistItem;
import com.hcmute.tech_shop.services.interfaces.WishlistItemService;
import com.hcmute.tech_shop.services.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/user/wishlist")
@RequiredArgsConstructor
public class WishlistRestController {
    private final WishlistService wishlistService;
    private final WishlistItemService wishlistItemService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertItemIntoWishlist(Model model,
                                                 @RequestParam("wishlistId") Long wishlistId,
                                                 @RequestParam("productId") Long productId){
        try{
            boolean inserted = wishlistItemService.insertItemIntoWishlist(wishlistId, productId);
            if (inserted){
                return ResponseEntity.ok("Sản phẩm đã được thêm vào Wishlist!");
            }else{
                return ResponseEntity.ok("Bạn đã thêm sản phẩm vào Wishlist rồi!");
            }
        }catch (NotFoundException ex){
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi lưu, sản phẩm hoặc kho wihslist không tồn tại!");
        }
    }
}
