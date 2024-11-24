package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.responses.WishlistResponse;
import com.hcmute.tech_shop.entities.CartDetail;
import com.hcmute.tech_shop.services.Impl.CartDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final CartDetailServiceImpl cartDetailService;
    @GetMapping
    public String showWishlist(Model model) {
        List<WishlistResponse> listWishlist = cartDetailService.getAllWishlist();
        model.addAttribute("listWishlist", listWishlist);
        return "/user/wishlist";
    }

}
