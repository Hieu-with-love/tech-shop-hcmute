package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.WishlistItemResponse;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.entities.WishlistItem;
import com.hcmute.tech_shop.services.Impl.WishlistItemServiceImpl;
import com.hcmute.tech_shop.services.Impl.WishlistServiceImpl;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistServiceImpl wishlistService;
    private final WishlistItemServiceImpl wishlistItemService;
    private final UserService userService;
    private final CartService cartService;
    private final ICartDetailService cartDetailService;

    public UserRequest getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .active(user.isActive())
                .image(user.getImage())
                .build();
        return userRequest;
    }

    @GetMapping
    public String showWishlists(Model model,
                                @RequestParam("wishlistId") Long wishlistId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDetailResponse> cartDetailList = new ArrayList<>();

        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            User user = userService.getUserByUsername(username);

            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null, BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailService.getAllItems(cartDetailService.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
            model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        List<WishlistItemResponse> wishlistItems = wishlistItemService.getItems(wishlistId);
        model.addAttribute("wishlistItems", wishlistItems);
        model.addAttribute("wishlistEmpty", wishlistItems.isEmpty());
        return "/user/wishlist";
    }



}
