package com.hcmute.tech_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userControllerOfAdmin")
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping("")
    public String userlist() {
        return "admin/users/userlists";
    }

    @GetMapping(value = "/add-user")
    public String addUser() {
        return "admin/users/newuser";
    }
}
