package com.hcmute.tech_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/index";
    }
}
