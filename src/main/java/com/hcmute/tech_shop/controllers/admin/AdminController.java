package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);
        return "admin/index";
    }

    @GetMapping(value = "/userlist")
    public String userlist() {
        return "admin/userlists";
    }

    @GetMapping(value = "/add-user")
    public String addUser() {
        return "admin/newuser";
    }

}
