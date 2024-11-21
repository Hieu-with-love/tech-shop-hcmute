package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CartDetailRequest;
import com.hcmute.tech_shop.dtos.requests.CartRequest;
import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller(value = "homeUserController")
@RequestMapping("/user")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ICartDetailService cartDetailServiceImpl;

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

    @GetMapping("/dashboard")
    public String home(Model model, HttpSession session) {
        List<Category> categories = categoryService.findAll();

        UserRequest userRequest = getUser();
        List<CartDetailResponse> cartDetailList = new ArrayList<>();
        int numberProductInCart = 0;
        Cart cart = new Cart();
        if(userRequest != null) {
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = new Cart();
                cart.setUserId(userRequest.getId());
                cart.setTotalPrice(BigDecimal.ZERO);
                cart = cartService.createCart(cart);
            }
            cartDetailList = cartDetailServiceImpl.getAllItems(cartDetailServiceImpl.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
        }

        model.addAttribute("categories", categories);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        return "/user/home";
    }
}
