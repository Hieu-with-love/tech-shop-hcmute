package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/home")
    public String home(Model model) {

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
    public String showPageMyAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        com.hcmute.tech_shop.entities.User user = userService.getUserByUsername(username);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        UserRequest userRequest = userService.convertToDto(user);
        model.addAttribute("userDto", userRequest);
        return "user/my-account";
    }

    // Làm chức năng gì thì đặt endpoint là, /user/<name-feature>
    /*
        Ví dụ: Làm chức năng chi tiết sản phẩm (detail-cart)
        -> Đặt tên: @GetMapping("/user/detail/cart") nhưng ở trên có "/user" rồi
        -> còn lại là @GetMapping("/detail/cart")
    * */
}
