package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.services.interfaces.WishlistItemService;
import com.hcmute.tech_shop.services.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.*;

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

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItemFromWishlist(@RequestParam Map<String, String> params){
        try{
            Long wishlistId = Long.parseLong(params.get("wishlistId"));
            Long productId = Long.parseLong(params.get("productId"));
            wishlistItemService.removeItemFromWishlist(wishlistId, productId);
            return ResponseEntity.ok("Xoá item khỏi Wishlist thành công");
        }catch (NotFoundException ex){
            return ResponseEntity.badRequest().body("Lôi ưn dụng, không xoá được");
        }
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearWishlist(@RequestParam Map<String, String> params){
        try{
            Long wishlistId = Long.parseLong(params.get("wishlistId"));
            wishlistService.clearWishlist(wishlistId);
            return ResponseEntity.ok("Clear successfully !");
        }catch (NotFoundException ex){
            return ResponseEntity.badRequest().body("Not found wishlist's id");
        }
    }

}
