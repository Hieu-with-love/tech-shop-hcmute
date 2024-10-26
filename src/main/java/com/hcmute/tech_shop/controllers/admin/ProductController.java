package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("") // localhost:8080/admin/products?page=1&limit=10
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/productlist";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "admin/products/add";
    }
}
