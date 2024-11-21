package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("userControllerOfAdmin")
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String userlist(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/userlists";
    }

    @GetMapping(value = "/add-user")
    public String addUser(Model model) {
        List<Role> roles = roleService.getAllRoles();
        UserRequest userRequest = new UserRequest();
        model.addAttribute("userRequest", userRequest);
        model.addAttribute("roles", roles);
        return "admin/users/newuser";
    }
}
