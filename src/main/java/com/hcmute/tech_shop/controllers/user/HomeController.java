package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.*;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller(value = "homeUserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ICartDetailService cartDetailServiceImpl;
    private final IOrderService orderService;
    private final IAddressService addressService;

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
        if (userRequest != null) {
            cart = cartService.findByCustomerId(userRequest.getId());
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userRequest.getId());
                cart.setTotalPrice(BigDecimal.ZERO);
                cart = cartService.createCart(cart);
            }
            cartDetailList = cartDetailServiceImpl.getAllItems(cartDetailServiceImpl.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if (cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
        }

        model.addAttribute("categories", categories);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        return "/user/home";
    }

    @GetMapping("/my-account")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        com.hcmute.tech_shop.entities.User user = userService.getUserByUsername(username);
        ProfileDto profileDto = ProfileDto.builder()
                .username(username)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .status(user.isActive())
                .image(user.getImage())
                .build();
        model.addAttribute("profileDto", profileDto);
        model.addAttribute("orders", orderService.findByUsername(username)
                .stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .toList());
        model.addAttribute("addresses", addressService.findByUser_Username(username));
        return "user/my-account";
    }

}
