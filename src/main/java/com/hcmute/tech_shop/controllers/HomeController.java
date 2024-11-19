package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final IProductService productService;

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

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {

        List<Category> categories = categoryService.findAll();

        UserRequest userRequest = getUser();
        List<CartDetail> cartDetailList = new ArrayList<>();
        List<CartDetail> cartDetailListFull = new ArrayList<>();
        Cart cart = new Cart();
        if(userRequest != null) {
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = new Cart();
                cart.setUserId(userRequest.getId());
                cart.setTotalPrice(BigDecimal.ZERO);
                cart = cartService.createCart(cart);
            }
            cartDetailList = cartDetailServiceImpl.findAllByCart_Id(cart.getId());
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
        }

        model.addAttribute("categories", categories);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("cartDetailListFull", cartDetailListFull);
        List<ProductResponse> products = productService.getAllProducts();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);
        session.setAttribute("cartId", 1);
        session.setAttribute("cart", cart);
        session.setAttribute("cartDetailList", cartDetailList);
        session.setAttribute("cartDetailListFull", cartDetailListFull);
        session.setAttribute("products", products);
        return "user/home";
    }

    @GetMapping("/not-found")
    public String notFound(Model model) {
        return "user/404";
    }

    @GetMapping("/my-account")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        com.hcmute.tech_shop.entities.User user = userService.getUserByUsername(username);
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
        model.addAttribute("userDto", userRequest);
        return "user/my-account";
    }

    @PostMapping("/my-account")
    public String updateProfile(Model model,
                                @Valid @ModelAttribute("userDto") UserRequest userDto,
                                BindingResult result) {
        if (result.hasErrors()){
            model.addAttribute("error", result.getAllErrors());
        }

        userService.updateProfile(userDto.getId(), userDto, result);
        return "user/my-account";
    }

    // Làm chức năng gì thì đặt endpoint là, /user/<name-feature>
    /*
        Ví dụ: Làm chức năng chi tiết sản phẩm (detail-cart)
        -> Đặt tên: @GetMapping("/user/detail/cart") nhưng ở trên có "/user" rồi
        -> còn lại là @GetMapping("/detail/cart")
    * */
}
