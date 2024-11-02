package com.hcmute.tech_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class HomeController {
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
    // Làm chức năng gì thì đặt endpoint là, /user/<name-feature>
    /*
        Ví dụ: Làm chức năng chi tiết sản phẩm (detail-cart)
        -> Đặt tên: @GetMapping("/user/detail/cart") nhưng ở trên có "/user" rồi
        -> còn lại là @GetMapping("/detail/cart")
    * */
}
