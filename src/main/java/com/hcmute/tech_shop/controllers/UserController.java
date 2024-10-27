package com.hcmute.tech_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login() {
        return "user/sign-in";
    }
    @GetMapping("/register")
    public String register(Model model) {

        return "user/sign-up";
    }
}
