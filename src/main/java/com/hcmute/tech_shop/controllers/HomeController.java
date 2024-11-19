package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final IProductService productService;

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<ProductResponse> products = productService.getAllProducts();
        session.setAttribute("user", user);
        session.setAttribute("cartId", 1);

        model.addAttribute("products", products);
        return "user/home";
    }
    @GetMapping("/test")
    public String test(Model model) {
        return "user/single-product-5";
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
                                @Valid @ModelAttribute UserRequest userDto,
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
