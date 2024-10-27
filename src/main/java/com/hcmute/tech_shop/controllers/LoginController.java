package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

// Viet tra ve cac form
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "user/sign-in";
    }

}
