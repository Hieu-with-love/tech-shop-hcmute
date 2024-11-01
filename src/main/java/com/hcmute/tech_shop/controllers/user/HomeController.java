package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller(value = "homeUserController")
@RequestMapping("/user")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/dashboard")
    public String home(Model model) {
        List<Category> categoryDTOList = categoryService.findAll();
        model.addAttribute("categories", categoryDTOList);
        return "/user/home";
    }
}
